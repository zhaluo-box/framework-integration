package org.framework.integration.example.rabbit.producer.config;

import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.HashMap;

/**
 * Created  on 2022/11/29 09:9:23
 *
 * @author zl
 */
@Configuration
public class ListenerConfig implements RabbitListenerConfigurer {

    @Value("${spring.application.name}")
    private String connectionName;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //        factory.setBatchListener(true);
        //        factory.setConsumerBatchEnabled(true);
        //        factory.setDeBatchingEnabled(true);
        factory.setReceiveTimeout(1000L);
        var messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper());
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public ClassMapper classMapper() {
        var classMapper = new DefaultJackson2JavaTypeMapper();
        classMapper.setTrustedPackages("*"); // 可以设置相信的包， DefaultJackson2JavaTypeMapper  默认只新人 java.util 与Java.lang
        var idClassMapping = new HashMap<String, Class<?>>();
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        // 这里的转换器设置实现了 通过 @Payload 注解 自动反序列化message body
        var mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        factory.setMessageConverter(mappingJackson2MessageConverter);
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public Queue directQueue() {
        return new Queue(QueueDeclare.DIRECT_QUEUE);
    }

    /**
     * 作为消费者 routeKey 的绑定不如注解方便，
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(ExchangeDeclare.DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding directBindingInfo() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("info");
    }

    @Bean
    public Binding directBindingWarn() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("warn");
    }

    @Bean
    public Binding directBindingDebug() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("debug");
    }

    @Bean
    public Binding directBindingError() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("error");
    }

}
