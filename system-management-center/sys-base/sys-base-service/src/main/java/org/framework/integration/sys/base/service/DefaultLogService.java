package org.framework.integration.sys.base.service;

import org.framework.integration.security.core.entity.AuthInfo;
import org.framework.integration.security.core.utils.JwtUtil;
import org.framework.integration.sys.base.common.service.LogService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * Created  on 2022/7/29 15:15:58
 *
 * @author wmz
 */
@Service
public class DefaultLogService implements LogService {

    /**
     * TODO 后期考虑是否将登录登出放到gateway 统一 ttl时间
     * 代码暂时采用mock的形式 实现token
     *
     * @param account  账户
     * @param password 密码
     * @return token
     */
    @Override
    public String login(String account, String password) {
        // 跨服务获取用户基础信息
        // 用户基础信息校验
        // 账户锁定|删除校验
        // 密码匹配校验

        // 包装认证信息
        var authInfo = getAuthInfo(null);
        // 创建token 返回
        String token = JwtUtil.createToken(Map.of(AuthInfo.class.getSimpleName(), authInfo), 60000000);
        // 记录登录日志
        recordLogInfo(null);
        return token;
    }

    @Override
    public void logout(String account) {

        //

    }

    /**
     * 账户信息 ==》 认证信息
     *
     * @param accountDTO 目前占位，到时候替换成account info
     * @return authInfo
     */
    private AuthInfo getAuthInfo(Object accountDTO) {
        // TODO  临时mock authInfo 后期实现具体逻辑
        return new AuthInfo().setAccount("120110")
                             .setAccountId(1)
                             .setAccountName("狗管理")
                             .setDeptName("系统管理")
                             .setDeptId(1)
                             .setRoles(new String[] { "manager", "admin" })
                             .setTokenId(UUID.randomUUID().toString());
    }

    /**
     * 记录登录信息
     *
     * @param accountInfo 账户信息
     */
    private void recordLogInfo(Object accountInfo) {

    }
}
