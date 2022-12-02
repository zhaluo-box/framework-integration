package org.framework.integration.example.rabbit.producer.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.GroupDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.framework.integration.example.rabbit.producer.entity.CustomMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created  on 2022/11/29 09:9:25
 *
 * @author zl
 */
@Slf4j
@Service
public class ConsumerService {

    /**
     * 简单消息队列 如果写多个监听者就会变为 work_queue 进行竞争消费
     */
    @RabbitListener(queues = QueueDeclare.SIMPLE_QUEUE, group = GroupDeclare.SIMPLE_GROUP_A)
    public void simpleQueueLister(String message) {
        log.info("{} : 简单消息测试: 消费者[1],消息 ： {}", GroupDeclare.SIMPLE_GROUP_A, message);
    }

    /**
     * 简单消息验证 变成work_queue
     */
    @RabbitListener(queues = QueueDeclare.SIMPLE_QUEUE, group = GroupDeclare.SIMPLE_GROUP_B)
    public void simpleQueueLister2(String message) {
        log.info("{} : 简单消息测试: 消费者[2],消息 ： {}", GroupDeclare.SIMPLE_GROUP_B, message);
    }

    /**
     * 配合Java config bean
     * java Config 对于exchange  绑定 routeKey 不太方便 需要自己硬编码 定义很多 binging 可以在config 定义好exchange 和 queue 之后使用注解来绑定
     */
    @RabbitListener(queues = QueueDeclare.DIRECT_QUEUE)
    public void directLogLister(BaseMessage<String> message) {
        log.info("direct-queue 测试 所有log 都接收 {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.SIMPLE_TOPIC), exchange = @Exchange(value = ExchangeDeclare.SIMPLE_TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC), key = {
                    "log.*" }))
    public void simpleTopic(@Payload BaseMessage<String> message) {
        log.info(" simple topic  测试  {}", message);
    }

    /**
     * 对于广播而言 广播是 交换机与队列之间 进行广播， 而订阅者则负责获取队列里面的内容，所有RabbitMQ 的一切都是基于队列这个维度的
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

    /**
     * 测试 消息转换失败了 如何处理 会丢失么
     * 手动ack:
     * 自动ack:
     */
    @RabbitListener(queues = QueueDeclare.CONVERT_FAIL_QUEUE)
    public void messageConverterFail(BigDecimal bigDecimal, Channel channel) {
        log.info("消息转换失败演示 : {}", bigDecimal);
    }

}
