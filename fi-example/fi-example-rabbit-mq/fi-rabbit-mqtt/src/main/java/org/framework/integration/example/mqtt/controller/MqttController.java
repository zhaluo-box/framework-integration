package org.framework.integration.example.mqtt.controller;

import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.example.mqtt.gateway.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2022/11/28 14:14:57
 *
 * @author zl
 */
@RestController("/mqtt/")
public class MqttController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MqttGateway mqttGateway;

    @PostMapping("/hello")
    public String hello() {
        return "hello!";
    }

    @PostMapping("/sendMqtt")
    public ResponseEntity<Void> sendMqtt(@RequestParam String sendData) {
        System.out.println(sendData);
        System.out.println("进入sendMqtt-------" + sendData);
        mqttGateway.sendToMqtt("topic01", sendData);
        return ResponseEntity.ok();
    }

    @PostMapping("/sendMqttTopic")
    public ResponseEntity<Void> sendMqtt(@RequestParam String sendData, @RequestParam String topic) {
        mqttGateway.sendToMqtt(topic, (String) sendData);
        return ResponseEntity.ok();
    }

}
