package org.framework.integration.fi.mg.feign.adapter;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created  on 2023/6/30 15:15:01
 *
 * @author zl
 */
@Configuration
@ComponentScan(basePackageClasses = { BeanConfig.class })
@EnableFeignClients(basePackages = "org.framework.integration.fi.mg.feign.adapter.client")
public class BeanConfig {

}
