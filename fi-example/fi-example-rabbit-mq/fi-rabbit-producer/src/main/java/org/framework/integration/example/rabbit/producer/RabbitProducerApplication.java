package org.framework.integration.example.rabbit.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created  on 2022/9/22 11:11:02
 *
 * @author zl
 */
@SpringBootApplication
public class RabbitProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitProducerApplication.class, args);
    }
}
