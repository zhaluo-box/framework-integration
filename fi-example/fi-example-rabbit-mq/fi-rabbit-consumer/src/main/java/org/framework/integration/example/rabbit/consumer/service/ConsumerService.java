package org.framework.integration.example.rabbit.consumer.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.framework.integration.example.rabbit.consumer.entity.CustomMessage;
import org.framework.integration.example.rabbit.consumer.entity.SubMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 点对点：
 * 简单消息模型 1：1
 * 竞争队列 1（n）:n
 * 发布订阅：
 * 广播 fanout
 * direct  routeKey 不支持通配
 * topic  支持通配符   .#  支持后续多个单词  .* 只支持一个 单词以 "." 分割
 * Created  on 2022/9/21 12:12:24
 *
 * @author zl
 */
@Slf4j
@Service
public class ConsumerService {

    /**
     * 简单消息队列 如果写多个监听者就会变为 work_queue 进行竞争消费
     */
    @RabbitListener(queues = QueueDeclare.SIMPLE_QUEUE)
    public void simpleQueueLister(String message) {
        log.info("简单消息测试: 消费者[1],消息 ： {}", message);
    }

    /**
     * 简单消息验证 编程work_queue
     */
    @RabbitListener(queues = QueueDeclare.SIMPLE_QUEUE)
    public void simpleQueueLister2(String message) {
        log.info("简单消息测试: 消费者[2],消息 ： {}", message);
    }

    @RabbitListener(queues = QueueDeclare.WORK_QUEUE)
    public void workQueueLister(String message) {
        log.info("work-queue消息测试: 消费者[1],消息 ： {}", message);
    }

    @RabbitListener(queues = QueueDeclare.WORK_QUEUE)
    public void workQueueLister2(String message) {
        log.info("work-queue消息测试: 消费者[2],消息 ： {}", message);
    }

    /**
     * 配合Java config bean
     * java Config 对于exchange  绑定 routeKey 不太方便 需要自己硬编码 定义很多 binging 可以在config 定义好exchange 和 queue 之后使用注解来绑定
     */
    @RabbitListener(queues = QueueDeclare.DIRECT_QUEUE)
    public void directLogLister(BaseMessage<String> message) {
        log.info("direct-queue 测试 所有log 都接收 {}", message);
    }

    /**
     * 支持多个队列绑定同一个key 实现广播
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.DIRECT_QUEUE2), exchange = @Exchange(value = ExchangeDeclare.DIRECT_EXCHANGE), key = {
                    "error", "debug" }))
    public void directLogLister2(BaseMessage<String> message) {
        log.error(" ================  direct-queue2 测试 error {}", message);
    }

    /**
     * 经过测试 fanout 模式下 route key 是没有作用的， 因为消息是发给交换机，交换机直接发给bind queue 不会使用route key
     * 前提是一定要声明对交换机的类型
     * 而且在绑定关系上是没有routeKey 的 可以查看rabbit  admin ui 查看bind
     *
     * @see ExchangeTypes
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.FANOUT_QUEUE1), exchange = @Exchange(value = ExchangeDeclare.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT), key = "1"))
    public void fanoutLister(@Payload CustomMessage customMessage, @Headers Map<String, Object> headers) {
        log.warn("message headers : {}", headers);
        log.info("自定义对象消费与fanout(广播)1 ： {} ", customMessage);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.FANOUT_QUEUE2), exchange = @Exchange(value = ExchangeDeclare.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT), key = "2"))
    public void fanoutLister2(@Payload CustomMessage customMessage, @Headers Map<String, Object> headers) {
        log.warn("message headers : {}", headers);
        log.error("自定义对象消费与fanout(广播)2 ： {} ", customMessage);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE4), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.*" }))
    public void topicLogLister(@Payload BaseMessage<String> message) {
        log.error(" **********  topic-queue 测试 * {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE1), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.#" }))
    public void topicLogLister2(@Payload BaseMessage<String> message) {
        log.warn(" ############  topic-queue 测试 # {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE2), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.info" }))
    public void topicLogLister3(@Payload BaseMessage<String> message) {
        log.info(" iiiiiiiiiiii  topic-queue 测试 info {}", message);
    }

    /**
     * 消费者监听, 以队列为维度进行监听  下面的方法能消费哪些消息 是队列决定的，毕竟真正绑定的是routeKey与queue 而不是下面的@queueBinding
     * 注解上的routeKey 只是用于绑定 下面的 topicLogLister4、5、6 虽然定义了不同的routeKey 但是是否消费消息，取决的还是rabbitMQ queue 绑定的routeKey
     * { QueueBinding 只做系统启动时的绑定 不做routeKey路由}
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE3), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.debug" }))
    public void topicLogLister4(@Payload BaseMessage<String> message) {
        log.debug(" ddddddddddd topic-queue 测试 debug {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE3), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "*.*" }))
    public void topicLogLister5(@Payload BaseMessage<String> message) {
        log.debug(" *.* topic-queue 测试  {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE3), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "*.*.*" }))
    public void topicLogLister6(@Payload BaseMessage<String> message) {
        log.debug(" *.*.* topic-queue 测试  {}", message);
    }

    @RabbitListener(queues = QueueDeclare.ABSTRACT_MESSAGE_QUEUE)
    public void abstractMessageListener(@Payload List<CustomMessage> message) {
        log.info("abstract message : {}", message);
    }

    @RabbitListener(queues = QueueDeclare.ABSTRACT_MESSAGE_QUEUE_2)
    public void abstractMessageListener(@Payload SubMessage message) {
        log.info("abstract message : {}", message);
    }

    /**
     * sendTo 可以转发给一个简单的队列
     */
    @RabbitListener(queues = QueueDeclare.DIRECT_QUEUE_D, ackMode = "MANUAL")
    @SendTo(QueueDeclare.SIMPLE_QUEUE)
    public String manualAck(CustomMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            System.out.println(" message = " + message);
            if (message.getAge() > 10) {
                throw new RuntimeException(" Ack Test Exception ");
            }
            /*
             * 第一个参数，消息的标识
             * RabbitMQ 推送消息给 Consumer 时,会附带一个 Delivery Tag，以便 Consumer可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了;
             * 第二个参数 multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认；
             * 如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认。
             */
            channel.basicAck(tag, false);
            return " sendTo : " + message.getName();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.info(" 消息消费失败 : {} ", message);
            /*
             * 第一个参数，消息的标识
             * 第二个参数是否批量处理消息,true:将一次性拒绝所有小于deliveryTag的消息。
             * 第三个参数，消息是否重入队列，false将消息存队列删除,如果绑定了死信队列会被投递到死信队列。true:消息被投递到堆列尾部，容易死循环，不建议,而且阻塞后续的队列
             * 123456
             * 123 4（nack） 44444... 4(ack)56
             */
            channel.basicNack(tag, false, false);
            //            channel.basicReject(tag, false); 一次性只能拒绝一条 requeue
            throw new RuntimeException("message is nack ");
        }
    }
}

//    /**
//     * 经过测试 argument 定义的死信队列没有被创建，还是需要自己定义，
//     * 但是可以减少一部分定义，建议队列与交换机的绑定关系，写在一起
//     */
//    // @formatter:off
//    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "d-queue", autoDelete = "true",
//                    arguments = {
//                        @Argument(name = "x-dead-letter-exchange", value = "dlx-temp"),
//                        @Argument(name="x-dead-letter-routing-key",value = "dlx")}),
//                    exchange = @Exchange(value = "d-tmp",autoDelete = "true"),key = "d"),  ackMode = "MANUAL")
//    // @formatter:on
//    public String manualAck2(CustomMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
//        try {
//            if (message.getAge() > 10) {
//                throw new RuntimeException(" Ack Test Exception ");
//            }
//            channel.basicAck(tag, false);
//            log.info("message : {} ", message);
//            return " sendTo : " + message.getName();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            log.info(" 消息消费失败 : {} ", message);
//            channel.basicNack(tag, true, false);
//            throw new RuntimeException("message is nack ");
//        }
//    }
