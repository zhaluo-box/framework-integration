package org.framework.integration.mp.starter.mateInject;

import org.framework.integration.mp.core.entity.SecurityInfo;
import org.framework.integration.mp.core.service.SecurityInfoProvider;
import org.framework.integration.web.common.security.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/5/30 16:16:25
 *
 * @author zl
 */
@Component
public class DefaultSecurityProvider implements SecurityInfoProvider {

    @Override
    public SecurityInfo getSecurityInfo() {
        var authInfo = SecurityContextHolder.getAuthInfo();
        return new SecurityInfo().setUserId(String.valueOf(authInfo.getAccountId())).setUserName(authInfo.getAccountName());
    }
}
