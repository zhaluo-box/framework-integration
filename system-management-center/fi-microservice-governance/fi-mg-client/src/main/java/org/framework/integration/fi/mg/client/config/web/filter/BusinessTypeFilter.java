package org.framework.integration.fi.mg.client.config.web.filter;

import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;
import org.framework.integration.fi.mg.common.enums.BusinessType;
import org.framework.integration.fi.mg.common.properties.MGSysOperationLogConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2023/5/29 16:16:55
 *
 * @author zl
 */
@Component
public class BusinessTypeFilter implements SysOperationLogFilter {

    @Autowired
    private MGSysOperationLogConfigProperties sysOperationLogConfigProperties;

    @Override
    public boolean filter(HttpServletRequest request, HandlerMethodInfo handlerMethodInfo) {
        var ignoredHttpMethods = getIgnoredHttpMethods(null);
        return !ignoredHttpMethods.contains(handlerMethodInfo.getBusinessType());
    }

    private List<BusinessType> getIgnoredHttpMethods(String[] args) {

        var global = sysOperationLogConfigProperties.getGlobalConfig().getIgnoreBusinessType();
        var local = sysOperationLogConfigProperties.getLocalConfig().getIgnoreBusinessType();

        var ignoredHttpMethods = new ArrayList<BusinessType>();

        ignoredHttpMethods.addAll(global);
        ignoredHttpMethods.addAll(local);

        return ignoredHttpMethods;
    }

}
