package com.example.rabbit.test.spring.converter;

import org.framework.integration.example.rabbit.producer.entity.CustomMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.SimpleMessageConverter;

/**
 * Created  on 2022/11/22 15:15:14
 *
 * @author zl
 */
public class SimpleMessageConverterTest {

    @Test
    @DisplayName("test simpleMessageConvert")
    public void testConverter() {
        var simpleMessageConverter = new SimpleMessageConverter();
        System.out.println(simpleMessageConverter);

        var customMessage = new CustomMessage().setAge(11);
        Message<?> toMessage = simpleMessageConverter.toMessage(customMessage, null);
        System.out.println("toMessage = " + toMessage);

        var fromMessage = simpleMessageConverter.fromMessage(toMessage, CustomMessage.class);
        System.out.println("fromMessage = " + fromMessage);
    }
}
