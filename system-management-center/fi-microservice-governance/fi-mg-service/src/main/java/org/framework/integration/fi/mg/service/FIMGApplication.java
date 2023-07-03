package org.framework.integration.fi.mg.service;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = "org.framework.integration.fi.mg.db.view.mapper")
public class FIMGApplication {
    public static void main(String[] args) {
        SpringApplication.run(FIMGApplication.class, args);
    }
}
