package org.framework.integration.web.common.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.framework.integration.security.core.entity.AuthInfo;

/**
 * Created  on 2022/8/9 13:13:44
 *
 * @author zl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityContextHolder {

    private final static ThreadLocal<AuthInfo> AUTH_INFO = new ThreadLocal<>();

    public static void setAuthInfo(AuthInfo authInfo) {
        AUTH_INFO.set(authInfo);
    }

    public static void remove() {
        AUTH_INFO.remove();
    }

    /**
     * 获取认证完整信息
     * <p>
     * 不包含 角色与tokenId 的信息
     * </p>
     */
    public static AuthInfo getAuthInfo() {
        return AUTH_INFO.get();
    }

    /**
     * 获取账户名称
     */
    public static String getAccountName() {
        var authInfo = AUTH_INFO.get();
        return authInfo == null ? "" : authInfo.getAccountName();
    }

    /**
     * 获取部门名称
     */
    public static String getDeptName() {
        var authInfo = AUTH_INFO.get();
        return authInfo == null ? "" : authInfo.getDeptName();
    }
}
