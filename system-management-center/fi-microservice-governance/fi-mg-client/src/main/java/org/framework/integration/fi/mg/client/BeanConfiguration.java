package org.framework.integration.fi.mg.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2023/5/25 13:13:38
 *
 * @author zl
 */
@Configuration
@ComponentScan(basePackages = "org.framework.integration.fi.mg.client")
@EnableConfigurationProperties
public class BeanConfiguration {

}
