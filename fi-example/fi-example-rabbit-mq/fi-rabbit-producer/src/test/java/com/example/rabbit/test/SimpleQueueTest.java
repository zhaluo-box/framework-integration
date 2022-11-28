package com.example.rabbit.test;

import com.example.rabbit.test.utils.RabbitConnectFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 对于使用系统默认交换机的队列 队列名称作为routeKey 使用
 * Created  on 2022/9/20 11:11:49
 *
 * @author wmz
 */

@Slf4j
public class SimpleQueueTest extends AbstractQueueTest {

    @Test
    @DisplayName("简单队列测试:生产者")
    public void simpleQueueProducerTest() {
        try (var connection = RabbitConnectFactory.getConnection(); var channel = connection.createChannel()) {
            log.info("简单消息队列，消息发送开始！");
            // durable      ： 持久化
            // exclusive    :  独占
            // autoDelete   :  服务器不再使用的时候自动删除
            channel.queueDeclare(SIMPLE_QUEUE, true, false, false, null);
            for (int i = 0; i < 10; i++) {
                var message = "简单消息队列测试" + i;
                channel.basicPublish("", SIMPLE_QUEUE, null, message.getBytes(StandardCharsets.UTF_8));
            }
            log.info("简单消息队列，消息发送完毕！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("simple queue Listener ")
    public void simpleQueueListener() {

        try (var connection = RabbitConnectFactory.getConnection(); var channel = connection.createChannel()) {
            log.info("简单消息队列，消息消费开始！");
            // 预取5条
            channel.basicQos(5);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("consumerTag = " + consumerTag);
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                try {
                    System.out.printf((SIMPLE_QUEUE) + "%s %n", message);
                } catch (Exception e) {
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                } finally {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(SIMPLE_QUEUE, false, deliverCallback, System.out::println);
            System.out.println("------");
            TimeUnit.SECONDS.sleep(10L);
            log.info("简单消息队列，消息消费完毕！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        consumer(SIMPLE_QUEUE, "消费者[1]： ", false);
        consumer(SIMPLE_QUEUE, "消费者[2]： ", false);
    }

}
