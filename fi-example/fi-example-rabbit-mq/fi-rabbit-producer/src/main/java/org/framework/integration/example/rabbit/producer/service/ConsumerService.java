package org.framework.integration.example.rabbit.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Created  on 2022/11/29 09:9:25
 *
 * @author zl
 */
@Slf4j
@Service
public class ConsumerService {

    /**
     * 简单消息验证 编程work_queue
     */
    @RabbitListener(queues = QueueDeclare.SIMPLE_QUEUE)
    public void simpleQueueLister2(String message) {
        log.info("简单消息测试: 消费者[1],消息 ： {}", message);
    }

    @RabbitListener(queues = QueueDeclare.WORK_QUEUE)
    public void workQueueLister(String message) {
        log.info("work-queue消息测试: 消费者[1],消息 ： {}", message);
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
}
