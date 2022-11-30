package org.framework.integration.example.mqtt5.config;

import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.Mqttv5PahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        var channelAdapter = new Mqttv5PahoMessageDrivenChannelAdapter(mqttConnectOptions, "mqtt-v5-inbound-01", "$share/a/topic01");
        var defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        var messageConverters = new ArrayList<MessageConverter>();
        messageConverters.add(defaultPahoMessageConverter);
        var compositeMessageConverter = new CompositeMessageConverter(messageConverters);
        channelAdapter.setMessageConverter(compositeMessageConverter);
        channelAdapter.setOutputChannel(outPutChannel());
        channelAdapter.setErrorChannel(errorChannel());
        channelAdapter.setManualAcks(false);
        channelAdapter.setPayloadType(MqttMessage.class);
        return channelAdapter;
    }

    @Bean
    public MessageChannel outPutChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outPutChannel")
    public MessageHandler handler() {
        return message -> System.out.println(" message = " + message.getPayload());
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "errorChannel")
    public MessageHandler errorHandle() {
        return message -> System.out.println("error message = " + new String((byte[]) message.getPayload(), StandardCharsets.UTF_8));
    }

    @Bean
    public MessageProducer inbound1() {
        var mqttConnectOptions = new MqttConnectionOptions();
        mqttConnectOptions.setUserName("admin");
        mqttConnectOptions.setPassword("123456".getBytes(StandardCharsets.UTF_8));
        mqttConnectOptions.setServerURIs(new String[] { MQTT_URL });
        var channelAdapter = new Mqttv5PahoMessageDrivenChannelAdapter(mqttConnectOptions, "mqtt-v5-inbound-11", "$share/a/topic01");
        var defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        var messageConverters = new ArrayList<MessageConverter>();
        messageConverters.add(defaultPahoMessageConverter);
        var compositeMessageConverter = new CompositeMessageConverter(messageConverters);
        channelAdapter.setMessageConverter(compositeMessageConverter);
        channelAdapter.setOutputChannel(outPutChannel1());
        channelAdapter.setErrorChannel(errorChannel1());
        channelAdapter.setManualAcks(false);
        channelAdapter.setPayloadType(MqttMessage.class);
        return channelAdapter;
    }

    @Bean
    public MessageChannel outPutChannel1() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outPutChannel1")
    public MessageHandler handler1() {
        return message -> System.out.println(" message1 = " + message.getPayload());
    }

    @Bean
    public MessageChannel errorChannel1() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "errorChannel1")
    public MessageHandler errorHandle1() {
        return message -> System.out.println("error message 1= " + new String((byte[]) message.getPayload(), StandardCharsets.UTF_8));
    }

    @Bean
    public MessageProducer inbound2() {
        var mqttConnectOptions = new MqttConnectionOptions();
        mqttConnectOptions.setUserName("admin");
        mqttConnectOptions.setPassword("123456".getBytes(StandardCharsets.UTF_8));
        mqttConnectOptions.setServerURIs(new String[] { MQTT_URL });
        var channelAdapter = new Mqttv5PahoMessageDrivenChannelAdapter(mqttConnectOptions, "mqtt-v5-inbound-22", "$share/b/topic01");
        var defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        var messageConverters = new ArrayList<MessageConverter>();
        messageConverters.add(defaultPahoMessageConverter);
        var compositeMessageConverter = new CompositeMessageConverter(messageConverters);
        channelAdapter.setMessageConverter(compositeMessageConverter);
        channelAdapter.setOutputChannel(outPutChannel2());
        channelAdapter.setErrorChannel(errorChannel2());
        channelAdapter.setManualAcks(false);
        channelAdapter.setPayloadType(MqttMessage.class);
        return channelAdapter;
    }

    @Bean
    public MessageChannel outPutChannel2() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "outPutChannel2")
    public MessageHandler handler2() {
        return message -> System.out.println(" message2 = " + message.getPayload());
    }

    @Bean
    public MessageChannel errorChannel2() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "errorChannel2")
    public MessageHandler errorHandle2() {
        return message -> System.out.println("error message 2= " + new String((byte[]) message.getPayload(), StandardCharsets.UTF_8));
    }

}
