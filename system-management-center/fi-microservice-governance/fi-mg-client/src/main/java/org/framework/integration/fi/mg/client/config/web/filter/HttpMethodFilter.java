package org.framework.integration.fi.mg.client.config.web.filter;

import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;
import org.framework.integration.fi.mg.common.properties.MGSysOperationLogConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2023/5/29 16:16:41
 *
 * @author zl
 */
@Component
public class HttpMethodFilter implements SysOperationLogFilter {

    @Autowired
    private MGSysOperationLogConfigProperties sysOperationLogConfigProperties;

    @Override
    public boolean filter(HttpServletRequest request, HandlerMethodInfo handlerMethodInfo) {
        List<String> ignoredHttpMethods = getIgnoredHttpMethods(null);
        String httpMethod = request.getMethod();
        return !ignoredHttpMethods.contains(httpMethod);
    }

    private List<String> getIgnoredHttpMethods(String[] excludeParamNames) {

        var global = sysOperationLogConfigProperties.getGlobalConfig().getIgnoreHttpMethods();
        var local = sysOperationLogConfigProperties.getLocalConfig().getIgnoreHttpMethods();

        var ignoredHttpMethods = new ArrayList<String>();

        ignoredHttpMethods.addAll(global);
        ignoredHttpMethods.addAll(local);

        return ignoredHttpMethods;
    }

}
