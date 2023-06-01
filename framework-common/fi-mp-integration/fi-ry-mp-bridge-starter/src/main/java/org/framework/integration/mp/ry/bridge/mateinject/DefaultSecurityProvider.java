package org.framework.integration.mp.ry.bridge.mateinject;

import com.ruoyi.common.security.utils.SecurityUtils;
import org.framework.integration.mp.core.entity.SecurityInfo;
import org.framework.integration.mp.core.service.SecurityInfoProvider;
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
        var authInfo = SecurityUtils.getLoginUser();
        return new SecurityInfo().setUserId(String.valueOf(authInfo.getUserid())).setUserName(authInfo.getUsername());
    }
}
