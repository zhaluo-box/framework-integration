package org.framework.integration.fi.mg.client.config.web;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.client.config.support.LogApplicationContextHolder;
import org.framework.integration.fi.mg.client.config.support.LogContextHolder;
import org.framework.integration.fi.mg.client.utils.ServletUtil;
import org.framework.integration.fi.mg.common.dto.SysOperationLogOriginalDTO;
import org.framework.integration.fi.mg.common.service.AbstractSysOperationLogSaveService;
import org.framework.integration.fi.mg.common.service.BusinessIdProvider;
import org.framework.integration.fi.mg.common.service.SysOperatorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

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
    private AbstractSysOperationLogSaveService sysOperationLogSaveService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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

        // operator info
        logContext.setStartTime(new Date())
                  .setOperatorId(sysOperatorProvider.getOperatorId())
                  .setOperatorName(sysOperatorProvider.getOperatorName())
                  .setName("TODO");// TODO @wmz 2023/5/26

        System.out.println("cn.hutool.extra.servlet.ServletUtil.getBody(request) = " + cn.hutool.extra.servlet.ServletUtil.getBody(request));
        Map<String, String> requestHeaders = ServletUtil.extractRequestHeaders(request);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        // http original info

        logContext.setOriginalRequestHeaders(requestHeaders).setRequestUrl(requestURI).setHttpMethod(httpMethod);

        if (log.isTraceEnabled()) {
            log.trace("请求头 :  {}", request.getHeaderNames().nextElement());
            log.trace("请求参数 :  {}", request.getParameterMap());
            log.trace("preHandle-log-context : {}", logContext);
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
        Map<String, String> responseHeaders = ServletUtil.extractResponseHeaders(response);
        logContext.setOriginalResponseHeader(responseHeaders).setEndTime(new Date());
        LogContextHolder.remove();
    }

}
