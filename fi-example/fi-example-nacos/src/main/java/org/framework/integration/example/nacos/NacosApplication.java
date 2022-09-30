package org.framework.integration.example.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created  on 2022/9/30 13:13:45
 *
 * @author zl
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class NacosApplication {

    public static void main(String[] args) {
        log.info("=======================================================");
        log.info("========no integrated fi-web, no need login===========");
        log.info("=======================================================");
        SpringApplication.run(NacosApplication.class, args);
    }
}
