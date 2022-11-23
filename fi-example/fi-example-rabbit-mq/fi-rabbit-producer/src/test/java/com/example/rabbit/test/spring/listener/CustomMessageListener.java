package com.example.rabbit.test.spring.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created  on 2022/11/22 14:14:46
 *
 * @author zl
 */
public class CustomMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("CustomMessageListener.onMessage");
        System.out.println(message);

    }

}
