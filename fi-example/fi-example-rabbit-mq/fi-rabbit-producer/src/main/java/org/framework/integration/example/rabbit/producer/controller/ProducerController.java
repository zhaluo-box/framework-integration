package org.framework.integration.example.rabbit.producer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
import org.framework.integration.example.rabbit.producer.entity.CustomMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2022/9/23 10:10:31
 *
 * @author zl
 */
@RestController("/producer/")
@Api(tags = "Rabbit 生产者测试")
public class ProducerController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 简单队列测试 走系统默认队列 routeKey 就队列名称即可
     */
    @ApiOperation("简单队列测试")
    @PostMapping("actions/simple-queues/")
    public ResponseEntity<Void> simpleQueue(@RequestBody BaseMessage<String> message) {
        amqpTemplate.convertAndSend(QueueDeclare.SIMPLE_QUEUE, message.getData());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("竞争队列测试")
    @PostMapping("actions/work-queues/")
    public ResponseEntity<Void> workQueue(@RequestBody BaseMessage<String> message) {
        amqpTemplate.convertAndSend(QueueDeclare.WORK_QUEUE, message.getData());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 经过测试routeKey 是不生效的 在rabbit admin ui 里面exchange 与 queue 的绑定关系里 没有routeKey
     */
    @PostMapping("actions/fanout/")
    @ApiOperation("广播测试")
    public ResponseEntity<Void> fanoutQueue(@RequestBody CustomMessage message) {
        if (message.getAge() == 1) {
            amqpTemplate.convertAndSend(ExchangeDeclare.FANOUT_EXCHANGE, "1", message);
        } else {
            amqpTemplate.convertAndSend(ExchangeDeclare.FANOUT_EXCHANGE, "2", message);
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
            amqpTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "info", message);
        }
        if (message.getTarget().equalsIgnoreCase("debug")) {
            amqpTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "debug", message);
        }
        if (message.getTarget().equalsIgnoreCase("warn")) {
            amqpTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "warn", message);
        }
        if (message.getTarget().equalsIgnoreCase("error")) {
            amqpTemplate.convertAndSend(ExchangeDeclare.DIRECT_EXCHANGE, "error", message);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 通配 Topic
     */
    @PostMapping("actions/topic/")
    @ApiOperation("路由交换")
    public ResponseEntity<Void> topicQueue(@RequestBody BaseMessage<String> message) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 复杂对象测试
     */
    @PostMapping("actions/custom-objects/")
    @ApiOperation("路由交换")
    public ResponseEntity<Void> customObject(@RequestBody CustomMessage message) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 死信队列测试
     */
    @PostMapping("actions/dead-letter/")
    @ApiOperation("路由交换")
    public ResponseEntity<Void> deadLetter(@RequestBody BaseMessage<String> message) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * confirm 测试
     */
    @PostMapping("actions/publish-confirm/")
    @ApiOperation("路由交换")
    public ResponseEntity<Void> publishConfirm(@RequestBody BaseMessage<String> message) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * tx 测试
     */
    @PostMapping("actions/tx/")
    @ApiOperation("路由交换")
    public ResponseEntity<Void> txTest(@RequestBody BaseMessage<String> message) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
