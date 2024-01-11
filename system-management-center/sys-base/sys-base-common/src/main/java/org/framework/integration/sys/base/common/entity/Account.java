package org.framework.integration.sys.base.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统账户信息
 * Created  on 2022/8/2 11:11:12
 *
 * @author zl
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    /**
     * ID
     */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 登录账号
     */
    private String loginAccount;

}
