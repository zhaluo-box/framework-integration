package org.framework.integration.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2022/8/2 15:15:08
 *
 * @author zl
 */
@Setter
@Getter
@Component
@RefreshScope
@ConfigurationProperties(prefix = "gateway.security.filter")
public class SecurityFilterProperties {

    /**
     * 白名单路径
     * 例如
     * - 登录
     * - 登出
     */
    private List<String> whitePath = new ArrayList<>();

}
