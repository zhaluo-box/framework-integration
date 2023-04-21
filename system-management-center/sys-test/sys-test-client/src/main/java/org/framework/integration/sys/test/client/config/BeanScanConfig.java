package org.framework.integration.sys.test.client.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2023/4/21 10:10:38
 *
 * @author zl
 */
@Configuration
@ComponentScan(basePackageClasses = BeanScanConfig.class)
public class BeanScanConfig {
    
}
