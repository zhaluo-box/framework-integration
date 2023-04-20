package org.framework.integration.sys.base.config;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created  on 2023/4/20 14:14:48
 *
 * @author zl
 */
@Component
public class MvcLogTraceInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 判定请求头中是否包含 log-trace-id, 如果没有则生成
        var logTraceId = request.getHeader(LogConstant.LOG_TRACE_ID);
        if (!StringUtils.hasText(logTraceId)) {
            logTraceId = getLogTraceId();
        }
        request.setAttribute(logTraceId, logTraceId);

        // 2. 放入MDC 即可
        MDC.put(LogConstant.TRACE_ID, logTraceId);
        return true;
    }

    private String getLogTraceId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    private String getHeader(HttpServletRequest request, String headerName) {
        Assert.notNull(request, "获取请求头，请求不能为空");
        String value = request.getHeader(headerName);
        if (!StringUtils.hasText(value)) {
            return "";
        }
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
