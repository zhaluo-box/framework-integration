package org.framework.integration.web.starter.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.security.core.constant.AuthHeaderConstants;
import org.framework.integration.security.core.entity.AuthInfo;
import org.framework.integration.web.common.security.SecurityContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created  on 2022/8/5 17:17:26
 *
 * @author zl
 */
@Slf4j
public class SecurityContextInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        AuthInfo authInfo = null;
        try {
            authInfo = extractAuthInfo(request);
            SecurityContextHolder.setAuthInfo(authInfo);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("认证信息： {}", authInfo);
                log.debug("请求方法： {}， 请求路径 ： {}", request.getMethod(), request.getRequestURI());
                log.debug(NestedExceptionUtils.buildMessage("认证信息解析异常", e));
            }
            throw new RuntimeException("认证信息解析异常", e);
        }

        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.remove();
        AsyncHandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private AuthInfo extractAuthInfo(HttpServletRequest request) {
        return new AuthInfo().setAccount(getHeader(request, AuthHeaderConstants.ACCOUNT))
                             .setAccountId(strToLong(getHeader(request, AuthHeaderConstants.ACCOUNT_ID)))
                             .setAccountName(getHeader(request, AuthHeaderConstants.ACCOUNT_NAME))
                             .setDeptName(getHeader(request, AuthHeaderConstants.DEPT_NAME))
                             .setDeptId(strToLong(getHeader(request, AuthHeaderConstants.DEPT_ID)));
    }

    private String getHeader(HttpServletRequest request, String headerName) {
        Assert.notNull(request, "获取请求头，请求不能为空");
        return request.getHeader(headerName);
    }

    /**
     * 如果为空，则返回0
     */
    private long strToLong(String headerValue) {
        if (StringUtils.hasText(headerValue)) {
            return 0;
        }
        return Long.parseLong(headerValue);
    }
}
