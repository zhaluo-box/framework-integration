package com.example.rabbit.test.spring.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Created  on 2022/11/22 14:14:59
 *
 * @author zl
 */
public class CustomMessageListenerAdapter extends MessageListenerAdapter {

    @Override
    public void setDelegate(Object delegate) {
        super.setDelegate(this);
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message);
    }

    @Override
    protected Object[] buildListenerArguments(Object extractedMessage, Channel channel, Message message) {
        return super.buildListenerArguments(extractedMessage, channel, message);
    }

    public void handleMethod() {

    }
}
