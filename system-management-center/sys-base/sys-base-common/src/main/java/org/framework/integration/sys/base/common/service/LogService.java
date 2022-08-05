package org.framework.integration.sys.base.common.service;

/**
 * Created  on 2022/7/29 15:15:57
 *
 * @author wmz
 */
public interface LogService {

    /**
     * 登录
     *
     * @param account  账户
     * @param password 密码
     * @return token
     */
    String login(String account, String password);

    /**
     * 登出
     *
     * @param account 账户
     */
    void logout(String account);
}
