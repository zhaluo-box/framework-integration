package com.example.rabbit.test;

import com.example.rabbit.test.utils.RabbitConnectFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * {@link <a href="https://github.com/rabbitmq/rabbitmq-tutorials">官方Demo</a>}
 * Created  on 2022/9/20 13:13:35
 *
 * @author wmz
 */
@Slf4j
public class AbstractQueueTest {

    public static final String SIMPLE_QUEUE = "test:simple:queue";

    public static final String WORK_QUEUE = "test:work:queue";

    public static final String DIRECT_QUEUE = "test:direct:queue";

    public static final String TOPIC_QUEUE = "test:topic:queue";

    public static final String RPC_QUEUE = "test:rpc:queue";

    public static final String PUBLISH_SUBSCRIBE_QUEUE = "test:publish:subscribe:queue";

    public static final String PUBLISH_CONFIRM_QUEUE = "test:publish:confirm:queue";

    /**
     * 交换机 : 广播
     */
    public static final String EXCHANGE_FANOUT_NAME = "test:exchange:fanout";

    /**
     * 交换机 : 路由
     */
    public static final String EXCHANGE_ROUTE_NAME = "test:exchange:route";

    public static final String EXCHANGE_TOPIC_NAME = "test:exchange:topic";

    public static final String EXCHANGE_DIRECT_NAME = "test:exchange:direct";

    public static void consumer(String queue, String consumerName, boolean sleep) throws Exception {
        var connection = RabbitConnectFactory.getConnection();
        var channel = connection.createChannel();
        // durable ： 持久化
        // exclusive : 独占
        // autoDelete : 服务器不再使用的时候自动删除
        channel.queueDeclare(queue, true, false, false, null);
        // 每次拉取一条,而不是一次性接收多条, 每次消费确认了之后才会再次获取消费下一条
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                System.out.printf((consumerName) + "%s %n", message);
                if (sleep) doWork();
            } catch (Exception e) {
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queue, false, deliverCallback, System.out::println);
    }

    public static void doWork() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @SneakyThrows
    public static void consumer(String queue, String consumerName, String exchangeName, String... routeKey) {
        Assert.isTrue(routeKey != null, "路由Key不能为null");
        var connection = RabbitConnectFactory.getConnection();
        var channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);
        for (String key : routeKey) {
            channel.queueBind(queue, exchangeName, key);
        }
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            var message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info("{} : {}", consumerName, message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, System.out::println);
    }

}
