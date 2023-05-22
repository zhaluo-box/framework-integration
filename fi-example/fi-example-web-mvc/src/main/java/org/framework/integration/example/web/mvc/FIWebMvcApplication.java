package org.framework.integration.example.web.mvc;

import org.framework.integration.example.web.mvc.utils.MessageUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created  on 2023/5/16 14:14:13
 *
 * @author zl
 */
@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FIWebMvcApplication {

    public FIWebMvcApplication(MessageSource messageSource) {
        MessageUtil.init(messageSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(FIWebMvcApplication.class, args);
    }
}
