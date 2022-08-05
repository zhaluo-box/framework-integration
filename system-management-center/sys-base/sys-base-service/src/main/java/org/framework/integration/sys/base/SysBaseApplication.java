package org.framework.integration.sys.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created  on 2022/8/2 10:10:51
 *
 * @author zl
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SysBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysBaseApplication.class, args);
    }
}
