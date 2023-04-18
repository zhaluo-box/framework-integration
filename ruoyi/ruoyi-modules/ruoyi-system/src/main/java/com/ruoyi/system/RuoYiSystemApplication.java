package com.ruoyi.system;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.ruoyi.system.mapper")
public class RuoYiSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuoYiSystemApplication.class, args);
    }
}
