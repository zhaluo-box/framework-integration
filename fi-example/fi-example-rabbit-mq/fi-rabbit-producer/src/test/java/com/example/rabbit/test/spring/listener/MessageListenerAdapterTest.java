package com.example.rabbit.test.spring.listener;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.ReplyingMessageListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * 消息监听者适配器
 * Created  on 2022/11/22 15:15:31
 *
 * @author zl
 */
public class MessageListenerAdapterTest {

    @Test
    @DisplayName("messageListenerAdapter  function support test")
    public void supplyActionTest() throws Exception {

        var messageListenerAdapter = new MessageListenerAdapter((ReplyingMessageListener<String, String>) data -> {
            System.out.println("123");
            return data;
        });
        //        messageListenerAdapter.setDefaultListenerMethod("handleMessage");
        messageListenerAdapter.setMessageConverter(new SimpleMessageConverter());
        var message = MessageBuilder.withBody("MessageListenerAdapter function support test".getBytes(StandardCharsets.UTF_8)).setHeader("", "").build();

        messageListenerAdapter.onMessage(message, null);
    }
    
}
