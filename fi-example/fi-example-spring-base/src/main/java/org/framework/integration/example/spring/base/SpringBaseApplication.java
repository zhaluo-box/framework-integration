package org.framework.integration.example.spring.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created  on 2023/8/16 16:16:38
 *
 * @author zl
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBaseApplication.class, args);
    }
}
