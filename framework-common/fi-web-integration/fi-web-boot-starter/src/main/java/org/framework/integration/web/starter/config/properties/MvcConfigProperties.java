package org.framework.integration.web.starter.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created  on 2022/8/19 16:16:38
 *
 * @author zl
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "mvc.interceptor")
public class MvcConfigProperties {

    private String excludePaths;

}
