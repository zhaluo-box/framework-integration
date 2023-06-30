package org.framework.integration.fi.mg.common.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import org.framework.integration.common.core.juc.NamedThreadFactory;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.common.dto.SysOperationLogOriginalDTO;
import org.framework.integration.fi.mg.common.properties.MGSysOperationLogConfigProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created  on 2023/5/25 16:16:01
 *
 * @author zl
 */
public abstract class AbstractSysOperationLogSaveService implements SysOperationLogSaveService, InitializingBean, DisposableBean {

    private ExecutorService executorService;

    @Autowired
    private MGSysOperationLogConfigProperties sysOperationLogConfigProperties;

    protected MGSysOperationLogConfigProperties.AsyncThreadPoolProperties getAsyncThreadPoolProperties() {
        return new MGSysOperationLogConfigProperties.AsyncThreadPoolProperties();
    }

    @Override
    public void destroy() throws Exception {

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    /**
     * 异步处理
     */
    public void asyncHandleLog(SysOperationLogOriginalDTO originalDTO) {
        executorService.execute(() -> {
            SysOperationLogDTO dto = transformer(originalDTO);
            saveBefore(dto);
            save(dto);
        });
    }

    /**
     * 同步处理
     */
    public void handleLog(SysOperationLogOriginalDTO originalDTO) {
        SysOperationLogDTO dto = transformer(originalDTO);
        saveBefore(dto);
        save(dto);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        MGSysOperationLogConfigProperties.AsyncThreadPoolProperties asyncThreadPoolProperties = getAsyncThreadPoolProperties();

        int coreSize = asyncThreadPoolProperties.getCoreSize();
        int maxPoolSize = asyncThreadPoolProperties.getMaximumPoolSize();
        long keepAliveTime = asyncThreadPoolProperties.getKeepAliveTime();
        int blockDequeSize = asyncThreadPoolProperties.getBlockDequeSize();

        // 拒绝策略  默认使用丢弃
        executorService = new ThreadPoolExecutor(coreSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<>(blockDequeSize),
                                                 new NamedThreadFactory("so-log"), new ThreadPoolExecutor.DiscardPolicy());

    }

    @Override
    public SysOperationLogDTO transformer(SysOperationLogOriginalDTO originalDTO) {

        SysOperationLogDTO sysOperationLogDTO = new SysOperationLogDTO();

        BeanUtils.copyProperties(originalDTO, sysOperationLogDTO);
        SimplePropertyPreFilter simplePropertyPreFilter = getSimplePropertyPreFilter(originalDTO.getExcludeParamNames());

        sysOperationLogDTO.setRequestHeaders(JSON.toJSONString(originalDTO.getOriginalRequestHeaders()))
                          .setRequestParam(JSON.toJSONString(originalDTO.getOriginalRequestParam()))
                          .setResponseHeaders(JSON.toJSONString(originalDTO.getOriginalResponseHeader()))
                          .setResponseData(JSON.toJSONString(originalDTO.getOriginalResponseData(), simplePropertyPreFilter));

        return sysOperationLogDTO;
    }

    public SimplePropertyPreFilter getSimplePropertyPreFilter(String[] excludeParamNames) {

        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().addAll(getExcludeParamNames(excludeParamNames));
        return simplePropertyPreFilter;
    }

    protected List<String> getExcludeParamNames(String[] excludeParamNames) {

        var global = sysOperationLogConfigProperties.getGlobalConfig().getPropertyFilter();
        var local = sysOperationLogConfigProperties.getLocalConfig().getPropertyFilter();

        var excludeProperties = new ArrayList<String>();

        excludeProperties.addAll(global);
        excludeProperties.addAll(local);
        excludeProperties.addAll(new ArrayList<>(Arrays.asList(excludeParamNames)));

        return excludeProperties;
    }
}
