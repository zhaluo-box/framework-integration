package org.framework.integration.fi.mg.client.config.web.filter;

import cn.hutool.extra.servlet.ServletUtil;
import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;
import org.framework.integration.fi.mg.common.properties.MGSysOperationLogConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created  on 2023/5/29 16:16:55
 *
 * @author zl
 */
@Component
public class RequestHeaderFilter implements SysOperationLogFilter {

    @Autowired
    private MGSysOperationLogConfigProperties sysOperationLogConfigProperties;

    @Override
    public boolean filter(HttpServletRequest request, HandlerMethodInfo handlerMethodInfo) {
        Map<String, String> ignoredHttpMethods = getIgnoredHttpMethods();

        Map<String, String> headerMap = ServletUtil.getHeaderMap(request);

        for (Map.Entry<String, String> entry : ignoredHttpMethods.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            var headerValue = headerMap.get(key);
            if (ObjectUtils.nullSafeEquals(headerValue, value)) {
                return false;
            }
        }

        return true;
    }

    private Map<String, String> getIgnoredHttpMethods() {

        var global = sysOperationLogConfigProperties.getGlobalConfig().getRequestHeaderFilter();
        var local = sysOperationLogConfigProperties.getLocalConfig().getRequestHeaderFilter();

        global.putAll(local);

        return global;
    }

}
