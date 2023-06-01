package org.framework.integration.fi.mg.client.config.web;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;
import org.framework.integration.fi.mg.client.config.properties.MGSysOperationLogConfigProperties;
import org.framework.integration.fi.mg.client.config.support.LogApplicationContextHolder;
import org.framework.integration.fi.mg.client.config.support.LogContextHolder;
import org.framework.integration.fi.mg.client.config.web.filter.SysOperationLogFilter;
import org.framework.integration.fi.mg.common.constants.HttpHeaderConstant;
import org.framework.integration.fi.mg.common.dto.SysOperationLogOriginalDTO;
import org.framework.integration.fi.mg.common.enums.InvokeWay;
import org.framework.integration.fi.mg.common.service.BusinessIdProvider;
import org.framework.integration.fi.mg.common.service.SysOperatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created  on 2023/5/25 17:17:08
 *
 * @author zl
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "mg.log", name = "enabled", havingValue = "true")
public class SysOperationLogInterceptor implements HandlerInterceptor {

    @Autowired
    private BusinessIdProvider businessIdProvider;

    @Autowired
    private SysOperatorProvider sysOperatorProvider;

    @Autowired
    private MGSysOperationLogConfigProperties mgSysOperationLogConfigProperties;

    @Autowired
    private List<SysOperationLogFilter> filters;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            SysOperationLogOriginalDTO logContext = LogContextHolder.getLogContext();

            // 如果不是handlerMethod 直接跳出
            if (!(handler instanceof HandlerMethod)) {
                logContext.setIgnored(true);
                return true;
            }

            Method method = ((HandlerMethod) handler).getMethod();

            // 如果是被忽略的方法，则直接跳出
            if (!LogApplicationContextHolder.hasMethod(method)) {
                logContext.setIgnored(true);
                return true;
            }

            HandlerMethodInfo handleMethodInfo = LogApplicationContextHolder.getHandleMethodInfo(method);

            // 过滤 如果过滤状态为false ,则忽略不再进行记录操作
            for (SysOperationLogFilter filter : filters) {
                boolean status = filter.filter(request, handleMethodInfo);
                if (!status) {
                    logContext.setIgnored(true);
                    return true;
                }
            }

            // operator & method  & system info
            logContext.setStartTime(new Date())
                      .setBusinessCode(businessIdProvider.getBusinessCode())
                      .setOperatorId(sysOperatorProvider.getOperatorId())
                      .setOperatorName(sysOperatorProvider.getOperatorName())
                      .setName(handleMethodInfo.getName())
                      .setHost(ServletUtil.getClientIP(request))
                      .setModuleName(mgSysOperationLogConfigProperties.getModuleName())
                      .setSystemName(mgSysOperationLogConfigProperties.getSystemName())
                      .setBusinessType(handleMethodInfo.getBusinessType())
                      .setGroupName(handleMethodInfo.getGroupName())
                      .setMethod(handleMethodInfo.getMethodName());

            String requestURI = request.getRequestURI();
            String httpMethod = request.getMethod();
            Map<String, List<String>> requestHeaders = ServletUtil.getHeadersMap(request);

            // http original info
            logContext.setOriginalRequestHeaders(requestHeaders)
                      .setHttpMethod(httpMethod)
                      .setRequestUrl(requestURI)
                      .setOriginalRequestParam(request.getParameterMap())
                      .setFromModuleName(request.getHeader(HttpHeaderConstant.FROM_MODULE))
                      .setInvokeHierarchy(getInvokeHierarchy(request))
                      .setInvokeWay(getInvokeWay(request));

        } catch (Exception e) {
            log.error("操作日志采集，数据提取填充失败！", e);
        }
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SysOperationLogOriginalDTO logContext = LogContextHolder.getLogContext();
        if (logContext.isIgnored()) {
            LogContextHolder.remove();
            return;
        }

        Map<String, Collection<String>> responseHeaders = ServletUtil.getHeadersMap(response);
        logContext.setOriginalResponseHeader(responseHeaders)
                  .setErrorMsg(extractErrorMessage(ex))
                  .setStatus(extractStatus(ex))
                  .setEndTime(new Date())
                  .setHttpCode(response.getStatus())
                  .setCostTime(DateUtil.between(logContext.getStartTime(), logContext.getEndTime(), DateUnit.MS, true));

        // TODO @wmz 2023/5/29
        // 调用保存

        LogContextHolder.remove();
    }

    private String extractErrorMessage(Exception ex) {

        if (Objects.isNull(ex)) {
            return null;
        }

        return ExceptionUtil.getMessage(ex);
    }

    private String getInvokeWay(HttpServletRequest request) {
        String value = request.getHeader(HttpHeaderConstant.INVOKE_WAY);

        if (StringUtils.hasText(value)) {
            return value;
        }

        return InvokeWay.OUTER.name();
    }

    /**
     * 由于 整个系统返回的都是 200  基于异常进行判定请求是否正常
     */
    private String extractStatus(Exception ex) {
        return Objects.isNull(ex) ? "success" : "fail";
    }

    private Integer getInvokeHierarchy(HttpServletRequest request) {
        String value = request.getHeader(HttpHeaderConstant.INVOKE_HIERARCHY);
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            log.error("调用层级解析异常", e);
        }
        return -1;
    }
}
