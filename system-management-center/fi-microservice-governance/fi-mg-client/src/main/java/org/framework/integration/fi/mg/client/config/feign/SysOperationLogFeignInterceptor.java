package org.framework.integration.fi.mg.client.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.common.constants.HttpHeaderConstant;
import org.framework.integration.fi.mg.common.enums.InvokeWay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created  on 2023/5/26 16:16:34
 *
 * @author zl
 */
@Slf4j
public class SysOperationLogFeignInterceptor implements RequestInterceptor {

    @Value("${spring.application.name}")
    private String moduleName;

    @Override
    public void apply(RequestTemplate template) {
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String invokeWay = request.getHeader(HttpHeaderConstant.INVOKE_WAY);
        // 如果当前调用方式请求头 非空，并且为外部调用，则代表下一个请求是内部调用
        if (StringUtils.hasText(invokeWay) && InvokeWay.OUTER.name().equals(invokeWay)) {
            template.header(HttpHeaderConstant.INVOKE_WAY, InvokeWay.INNER.name());
        }
        template.header(HttpHeaderConstant.FROM_MODULE, moduleName);
        template.header(HttpHeaderConstant.INVOKE_HIERARCHY, getNextHierarchy(request));
    }

    /**
     * @return 获取下一层级
     */
    private String getNextHierarchy(HttpServletRequest request) {
        String hierarchy = request.getHeader("");

        //  如果头信息不存在， 则代表是从gateway或者直接调用当前模块，当前层级为1 下次层级理应为2
        if (!StringUtils.hasText(hierarchy)) {
            return Integer.toString(2);
        }

        try {
            int i = Integer.parseInt(hierarchy);
            int next = i + 1;
            return Integer.toString(next);
        } catch (Exception e) {
            log.error("调用层级解析失败，非整数字符， 头信息 ： " + hierarchy);
            // 解析报错直接返回-1
            return Integer.toString(-1);
        }

    }
}
