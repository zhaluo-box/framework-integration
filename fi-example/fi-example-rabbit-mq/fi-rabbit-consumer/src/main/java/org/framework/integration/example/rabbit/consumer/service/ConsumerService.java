package org.framework.integration.example.rabbit.consumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.framework.integration.example.rabbit.consumer.entity.CustomMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

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
    public void fanoutLister(@Payload CustomMessage customMessage, MessageProperties messageProperties, Channel channel, @Headers Map<String, Object> headers) {
        log.warn("message headers : {}", headers);
        log.info("自定义对象消费与fanout(广播)1 ： {} ", customMessage);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.FANOUT_QUEUE2), exchange = @Exchange(value = ExchangeDeclare.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT), key = "2"))
    public void fanoutLister2(@Payload CustomMessage customMessage, MessageProperties messageProperties, Channel channel,
                              @Headers Map<String, Object> headers) {
        log.warn("message headers : {}", headers);
        log.error("自定义对象消费与fanout(广播)2 ： {} ", customMessage);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE4), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.*" }))
    public void topicLogLister(@Payload BaseMessage<String> message, MessageProperties messageProperties) {
        log.error(" **********  topic-queue 测试 * {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE1), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.#" }))
    public void topicLogLister2(@Payload BaseMessage<String> message, MessageProperties messageProperties) {
        log.warn(" ############  topic-queue 测试 # {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE2), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.info" }))
    public void topicLogLister3(@Payload BaseMessage<String> message, MessageProperties messageProperties) {
        log.info(" iiiiiiiiiiii  topic-queue 测试 info {}", message);
    }

    /**
     * 消费者监听, 以队列为维度进行监听  下面的方法能消费哪些消息 是队列决定的，毕竟真正绑定的是routeKey与queue 而不是下面的@queueBinding
     * 注解上的routeKey 只是用于绑定 下面的 topicLogLister4、5、6 虽然定义了不同的routeKey 但是是否消费消息，取决的还是rabbitMQ queue 绑定的routeKey
     * { QueueBinding 只做系统启动时的绑定 不做routeKey路由}
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE3), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "topic.debug" }))
    public void topicLogLister4(@Payload BaseMessage<String> message, MessageProperties messageProperties) {
        log.debug(" ddddddddddd topic-queue 测试 debug {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE3), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "*.*" }))
    public void topicLogLister5(@Payload BaseMessage<String> message, MessageProperties messageProperties) {
        log.debug(" *.* topic-queue 测试  {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.TOPIC_QUEUE3), exchange = @Exchange(value = ExchangeDeclare.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "*.*.*" }))
    public void topicLogLister6(@Payload BaseMessage<String> message, MessageProperties messageProperties) {
        log.debug(" *.*.* topic-queue 测试  {}", message);
    }
}
