package org.framework.integration.gateway.config;

import io.jsonwebtoken.lang.Assert;
import lombok.Data;
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
@Data
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

    /**
     * 默认是10分钟刷新一次token
     */
    private long ttl = 10 * 60 * 1000;

    /**
     * 刷新因子
     */
    private byte tokenRefreshFactor = 1;

    public void setTokenRefreshFactor(byte factor) {
        Assert.isTrue(factor > 0 && factor < 10, "token 刷新因子，应该大于0且小于10");
    }

    public long getRefreshOffset() {
        return (long) Math.floor(ttl * (tokenRefreshFactor / 10.0F));
    }
}
