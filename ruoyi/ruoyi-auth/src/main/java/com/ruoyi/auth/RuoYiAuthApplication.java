package com.ruoyi.auth;

import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 认证授权中心
 *
 * @author ruoyi
 */
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuoYiAuthApplication.class, args);
    }
}
