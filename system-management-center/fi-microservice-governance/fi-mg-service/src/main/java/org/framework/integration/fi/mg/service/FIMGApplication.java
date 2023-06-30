package org.framework.integration.fi.mg.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created  on 2023/5/25 10:10:42
 *
 * @author zl
 */
@EnableDiscoveryClient
@SpringBootApplication
public class FIMGApplication {
    public static void main(String[] args) {
        SpringApplication.run(FIMGApplication.class, args);
    }
}
