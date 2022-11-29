package org.framework.integration.example.mqtt5.config;

import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * Created  on 2022/11/29 16:16:02
 *
 * @author zl
 */
@Configuration
public class MqttConfig {

    private static final String MQTT_URL = "tcp://127.0.0.1:1883";

    @Bean
    public MessageProducer inbound() {
        var mqttConnectOptions = new MqttConnectionOptions();
        mqttConnectOptions.setUserName("admin");
        mqttConnectOptions.setPassword("123456".getBytes(StandardCharsets.UTF_8));
        mqttConnectOptions.setServerURIs(new String[] { MQTT_URL });
        var channelAdapter = new Mqttv5PahoMessageDrivenChannelAdapter(mqttConnectOptions, "mqtt-v5-inbound-01", "topic01");
        channelAdapter.setMessageConverter(new MappingJackson2MessageConverter());
        channelAdapter.setOutputChannel(outPutChannel());
        channelAdapter.setErrorChannel(errorChannel());
        channelAdapter.setManualAcks(false);
        channelAdapter.setPayloadType(String.class);
        return channelAdapter;
    }

    @Bean
    public MessageChannel outPutChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outPutChannel")
    public MessageHandler handler() {
        return message -> System.out.println("error message = " + message);
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "errorChannel")
    public MessageHandler errorHandle() {
        return message -> System.out.println("error message = " + message);
    }

}
