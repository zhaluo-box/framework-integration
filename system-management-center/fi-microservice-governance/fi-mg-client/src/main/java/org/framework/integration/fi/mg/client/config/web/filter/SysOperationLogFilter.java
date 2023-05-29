package org.framework.integration.fi.mg.client.config.web.filter;

import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created  on 2023/5/25 11:11:45
 *
 * @author zl
 */
public interface SysOperationLogFilter {

    /**
     * @return true  记录操作日志， false ： 不记录操作日志
     */
    boolean filter(HttpServletRequest request, HandlerMethodInfo handlerMethodInfo);

}
