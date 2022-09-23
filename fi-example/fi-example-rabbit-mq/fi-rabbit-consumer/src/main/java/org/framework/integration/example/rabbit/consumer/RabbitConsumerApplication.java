package org.framework.integration.example.rabbit.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created  on 2022/9/23 10:10:00
 *
 * @author zl
 */
@SpringBootApplication
public class RabbitConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitConsumerApplication.class, args);
    }
}
