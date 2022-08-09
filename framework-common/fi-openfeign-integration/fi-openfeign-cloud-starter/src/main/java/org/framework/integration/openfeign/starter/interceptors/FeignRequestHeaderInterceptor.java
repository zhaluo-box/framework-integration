package org.framework.integration.openfeign.starter.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.framework.integration.security.core.constant.AuthHeaderConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * Created  on 2022/8/9 16:16:42
 *
 * @author zl
 */
@Component
public class FeignRequestHeaderInterceptor implements RequestInterceptor {

    final static List<String> AUTH_HEADERS = List.of(AuthHeaderConstants.ACCOUNT_NAME, AuthHeaderConstants.ACCOUNT, AuthHeaderConstants.ACCOUNT_ID,
                                                     AuthHeaderConstants.DEPT_NAME, AuthHeaderConstants.DEPT_ID);

    @Override
    public void apply(RequestTemplate template) {
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        var request = requestAttributes.getRequest();
        // 认证信息传递
        AUTH_HEADERS.forEach(header -> template.header(header, request.getHeader(header)));
    }

}
