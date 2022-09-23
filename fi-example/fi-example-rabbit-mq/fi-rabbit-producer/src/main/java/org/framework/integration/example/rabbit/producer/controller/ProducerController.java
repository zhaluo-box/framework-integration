package org.framework.integration.example.rabbit.producer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.dto.BaseMessage;
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
     * 简单队列测试
     */
    @PostMapping("actions/simple-queue/")
    @ApiOperation("简单队列测试")
    public ResponseEntity<Void> simpleQueue(@RequestBody BaseMessage<String> message) {
        amqpTemplate.convertAndSend(ExchangeDeclare.SIMPLE_EXCHANGE, "", message.getData());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("actions/fanout/")
    @ApiOperation("广播测试")
    public ResponseEntity<Void> fanoutQueue(@RequestBody BaseMessage<String> message) {
        amqpTemplate.convertAndSend(ExchangeDeclare.WORK_EXCHANGE, "", message.getData());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 路由 direct route
     */
    @PostMapping("actions/direct/")
    @ApiOperation("路由交换")
    public ResponseEntity<Void> directQueue(@RequestBody BaseMessage<String> message) {
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
    public ResponseEntity<Void> customObject(@RequestBody BaseMessage<String> message) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 私信队列测试
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
