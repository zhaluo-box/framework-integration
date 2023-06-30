package org.framework.integration.fi.mg.feign.adapter.service;

import com.ruoyi.common.security.utils.SecurityUtils;
import org.framework.integration.fi.mg.common.service.SysOperatorProvider;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/6/30 15:15:55
 *
 * @author zl
 */
@Component
public class RySysOperatorProvider implements SysOperatorProvider {

    @Override
    public String getOperatorId() {
        return SecurityUtils.getUserId().toString();
    }

    @Override
    public String getOperatorName() {
        return SecurityUtils.getLoginUser().getSysUser().getNickName();
    }
}
