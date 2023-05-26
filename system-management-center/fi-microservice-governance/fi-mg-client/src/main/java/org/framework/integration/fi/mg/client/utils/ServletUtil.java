package org.framework.integration.fi.mg.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created  on 2023/5/26 11:11:47
 *
 * @author zl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServletUtil {

    public static Map<String, String> extractRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        var result = new HashMap<String, String>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    public static Map<String, String> extractResponseHeaders(HttpServletResponse response) {
        var headerNames = response.getHeaderNames();
        var responseHeaders = new HashMap<String, String>(headerNames.size());
        headerNames.forEach(headerName -> responseHeaders.put(headerName, response.getHeader(headerName)));
        return responseHeaders;
    }

}
