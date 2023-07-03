package org.framework.integration.fi.mg.client;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.common.properties.MGSysOperationLogConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2023/5/25 13:13:38
 *
 * @author zl
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = { "org.framework.integration.fi.mg.client" })
@EnableConfigurationProperties
public class BeanConfiguration {

    @RefreshScope
    @Bean
    @ConfigurationProperties(prefix = MGSysOperationLogConfigProperties.PREFIX_NAME)
    public MGSysOperationLogConfigProperties mgSysOperationLogConfigProperties() {
        log.info("mg-operation-log-config init!");
        return new MGSysOperationLogConfigProperties();
    }

    static {
        log.info("fi-mg-client init config!");
    }

}
