package org.framework.integration.example.mqtt.config;

/**
 * Created  on 2022/11/28 14:14:43
 *
 * @author zl
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取yml
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.mqtt")     //对应yml文件中的com下的mqtt文件配置
public class MqttConfiguration {
    
    private String url;

    private String clientId;

    private String topics;

    private String username;

    private String password;

    private String timeout;

    private String keepalive;
}
