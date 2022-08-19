package org.framework.integration.security.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created  on 2022/7/29 16:16:34
 *
 * @author wmz
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ToString
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
     * 只用于gateway 登录状态判定
     */
    private String tokenId;

    /**
     * 角色列表
     * 用于权限过滤，gateway 基于AntMatch实现
     */
    private String[] roles;

}
