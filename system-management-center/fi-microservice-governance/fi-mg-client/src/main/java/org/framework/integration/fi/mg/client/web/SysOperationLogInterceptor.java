package org.framework.integration.fi.mg.client.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created  on 2023/5/25 17:17:08
 *
 * @author zl
 */
@Slf4j
public class SysOperationLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, String> requestHeaders = extractRequestHeaders(request);

        if (log.isTraceEnabled()) {
            log.trace("请求头 :  {}", request.getHeaderNames().nextElement());
            log.trace("请求参数 :  {}", request.getParameterMap());
            log.trace("preHandle-log-context : {}", LogContextHolder.getLocalMap());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Map<String, String> responseHeaders = extractResponseHeaders(response);

        LogContextHolder.remove();

    }

    public Map<String, String> extractRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        var result = new HashMap<String, String>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    public Map<String, String> extractResponseHeaders(HttpServletResponse response) {
        var headerNames = response.getHeaderNames();
        var responseHeaders = new HashMap<String, String>(headerNames.size());
        headerNames.forEach(headerName -> responseHeaders.put(headerName, response.getHeader(headerName)));
        return responseHeaders;
    }

}
