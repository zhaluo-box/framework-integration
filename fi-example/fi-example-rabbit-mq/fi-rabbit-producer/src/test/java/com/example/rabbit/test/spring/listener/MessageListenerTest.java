package com.example.rabbit.test.spring.listener;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created  on 2022/11/23 15:15:41
 *
 * @author zl
 */
public class MessageListenerTest {

    /**
     * 报错 可能是配置环节缺少一些东西
     */
    @Test
    @DisplayName("SimpleMessageListenerContainer  Test")
    public void testSimpleListenerContainer() throws InterruptedException {
        var context = new AnnotationConfigApplicationContext();
        context.register(AmqpConfig.class);
        context.refresh();
        TimeUnit.SECONDS.sleep(5000);
        context.close();
    }
}
