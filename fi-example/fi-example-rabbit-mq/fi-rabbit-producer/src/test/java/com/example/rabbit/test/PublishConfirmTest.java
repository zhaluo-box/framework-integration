package com.example.rabbit.test;

import com.example.rabbit.test.utils.RabbitConnectFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * 生产者 消息发布确认测试
 * Created  on 2022/11/21 13:13:57
 *
 * @author zl
 */
@Slf4j
public class PublishConfirmTest {

    @Test
    @DisplayName("publish confirm error route test")
    public void errorRouteTest() {
        try (var connection = RabbitConnectFactory.getFiConnection(); var channel = connection.createChannel()) {
            log.info("简单消息队列，消息发送开始！");
            // 开启发布确认
            channel.confirmSelect();
            var message = "正确的交换机 错误的routeKey 测试";
            channel.basicPublish("fi:example:direct-exchange11", "SIMPLE_QUEUE", null, message.getBytes(StandardCharsets.UTF_8));
            channel.addConfirmListener((tag, ack) -> {
                log.info(" ACK listener :  tag : {}, ack : {}", tag, ack);

            }, (tag, nack) -> {
                log.info(" NACK listener :  tag : {}, nack : {}", tag, nack);
            });

            log.info("简单消息队列，消息发送完毕！");
            channel.waitForConfirmsOrDie(5_000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @DisplayName("publish confirm error exchange test")
    public void errorExchangeTest() {
        try (var connection = RabbitConnectFactory.getConnection(); var channel = connection.createChannel()) {
            log.info("简单消息队列，消息发送开始！");
            // durable      ： 持久化
            // exclusive    :  独占
            // autoDelete   :  服务器不再使用的时候自动删除
            var message = "正确的交换机 错误的routeKey 测试";
            channel.basicPublish("fi:example:direct-exchange12", "info", null, message.getBytes(StandardCharsets.UTF_8));
            log.info("简单消息队列，消息发送完毕！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
