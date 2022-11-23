package com.example.rabbit.test.spring.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * 带有channel的消息监听器
 * Created  on 2022/11/22 14:14:53
 *
 * @author zl
 */
public class CustomChannelAwareMessageListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        System.out.println("CustomChannelAwareMessageListener.onMessage");
        System.out.println(message);
    }
}
