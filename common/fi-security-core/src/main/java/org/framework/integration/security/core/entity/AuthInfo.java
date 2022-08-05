package org.framework.integration.security.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created  on 2022/7/29 16:16:34
 *
 * @author wmz
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class AuthInfo {

    /**
     * 账户ID
     */
    private long accountId;

    /**
     * 账户
     */
    private String account;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 直属部门名称
     */
    private String deptName;

    /**
     * 直属部门Id
     */
    private long deptId;

    /**
     * tokenId 用于后期tokenStore
     */
    private String tokenId;

    /**
     * 角色列表
     */
    private String[] roles;

}
