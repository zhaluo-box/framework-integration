package org.framework.integration.example.rabbit.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Created  on 2022/9/21 12:12:24
 *
 * @author wmz
 */
@Slf4j
@Service
public class ConsumerService {

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.SIMPLE_QUEUE), exchange = @Exchange(value = ExchangeDeclare.SIMPLE_EXCHANGE), key = ""))
    public void simpleQueueLister(String message) {
        log.info("简单消息测试: 消费者[1],消息 ： {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.SIMPLE_QUEUE), exchange = @Exchange(value = ExchangeDeclare.SIMPLE_EXCHANGE), key = ""))
    public void simpleQueueLister2(String message) {
        log.info("简单消息测试: 消费者[2],消息 ： {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.WORK_QUEUE), exchange = @Exchange(value = ExchangeDeclare.WORK_EXCHANGE), key = ""))
    public void workQueueLister(String message) {
        log.info("work-queue消息测试: 消费者[1],消息 ： {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = QueueDeclare.WORK_QUEUE), exchange = @Exchange(value = ExchangeDeclare.WORK_EXCHANGE), key = ""))
    public void workQueueLister2(String message) {
        log.info("work-queue简单消息测试: 消费者[2],消息 ： {}", message);
    }

    /**
     * 配合Java config bean
     * java Config 对于exchange  绑定 routeKey 不太方便 需要自己硬编码 定义很多 binging
     */
    @RabbitListener(queues = QueueDeclare.DIRECT_QUEUE)
    public void directLogLister(BaseMessage<String> message) {
        log.info("direct-queue 测试 所有log 都接收 {}", message);
    }
}
