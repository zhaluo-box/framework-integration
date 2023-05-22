package org.framework.integration.example.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created  on 2023/5/19 15:15:34
 *
 * @author zl
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SleuthExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleuthExampleApplication.class, args);
    }
}
