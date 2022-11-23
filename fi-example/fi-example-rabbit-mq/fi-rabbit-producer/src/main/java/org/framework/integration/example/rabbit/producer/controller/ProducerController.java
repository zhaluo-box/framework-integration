package org.framework.integration.example.rabbit.producer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.framework.integration.example.rabbit.producer.config.ConfirmCallback;
import org.framework.integration.example.rabbit.producer.entity.CustomMessage;
import org.framework.integration.example.rabbit.producer.service.MessagePublishService;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2022/9/23 10:10:31
 *
 * @author zl
 */
@Slf4j
@RestController("/producer/")
@Api(tags = "Rabbit 生产者测试")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessagePublishService messagePublishService;

    /**
     * 简单队列测试 走系统默认队列 routeKey 就队列名称即可
     */
    @ApiOperation("简单队列测试")
    @PostMapping("actions/simple-queues/")
    public ResponseEntity<Void> simpleQueue(@RequestBody BaseMessage<String> message) {
        rabbitTemplate.convertAndSend(QueueDeclare.SIMPLE_QUEUE, message.getData());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("竞争队列测试")
    @PostMapping("actions/work-queues/")
    public ResponseEntity<Void> workQueue(@RequestBody BaseMessage<String> message) {
        rabbitTemplate.convertAndSend(QueueDeclare.WORK_QUEUE, message.getData());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 经过测试routeKey 是不生效的 在rabbit admin ui 里面exchange 与 queue 的绑定关系里 没有routeKey
     */
    @PostMapping("actions/fanout/")
    @ApiOperation("广播测试")
    public ResponseEntity<Void> fanoutQueue(@RequestBody CustomMessage message) {
        if (message.getAge() == 1) {
            rabbitTemplate.convertAndSend(ExchangeDeclare.FANOUT_EXCHANGE, "1", message);
        } else {
            rabbitTemplate.convertAndSend(ExchangeDeclare.FANOUT_EXCHANGE, "2", message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 路由 direct route
     * 对于自定义的payload 需要实现序列化接口，否则投递会异常
     */
    @PostMapping("actions/direct/")
    @ApiOperation("路由交换 direct route")
    public ResponseEntity<Void> directQueue(@RequestBody BaseMessage<String> message) {
        if (message.getTarget().equalsIgnoreCase("info")) {
            rabbitTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "info", message);
        }
        if (message.getTarget().equalsIgnoreCase("debug")) {
            rabbitTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "debug", message);
        }
        if (message.getTarget().equalsIgnoreCase("warn")) {
            rabbitTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "warn", message);
        }
        if (message.getTarget().equalsIgnoreCase("error")) {
            rabbitTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "error", message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 通配 Topic
     */
    @PostMapping("actions/topic/")
    @ApiOperation("通配 topic")
    public ResponseEntity<Void> topicQueue(@RequestBody BaseMessage<String> message) {
        rabbitTemplate.convertAndSend(ExchangeDeclare.TOPIC_EXCHANGE, message.getTarget(), message);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * confirm 测试
     * 向一个未声明的队列投递消息
     */
    @PostMapping("actions/publish-confirm/")
    @ApiOperation("发布确认")
    public ResponseEntity<Void> publishConfirm(@RequestBody BaseMessage<String> message) {
        CorrelationData crd = new CorrelationData();
        var data = message.getData();
        MessagePostProcessor messagePostProcessor = msg -> msg;
        var routeKey = message.getTarget();
        rabbitTemplate.convertAndSend(message.getSource(), routeKey, data, messagePostProcessor, crd);
        var confirmCallback = new ConfirmCallback().setExchange(message.getSource()).setRouteKey(routeKey).setPayload(data).setMessagePostProcessor(null);
        crd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("【回调】消息发送失败： {}", ex.getMessage());
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                log.info("【成功回调】: ACK : {}", result.isAck());
                log.info("【成功回调】: Reason: {}", result.getReason());
                log.info("【成功回调】 CRD : {}", crd);
            }
        });
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * tx 测试
     */
    @PostMapping("actions/tx/")
    @ApiOperation("事务测试")
    public ResponseEntity<Void> txTest(@RequestBody BaseMessage<String> message) {
        messagePublishService.txView(message);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 死信队列测试
     */
    @PostMapping("actions/dead-letter/")
    @ApiOperation("死信队列")
    public ResponseEntity<Void> deadLetter(@RequestBody BaseMessage<String> message) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
