package org.framework.integration.example.rabbit.consumer.config;

import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * AMQP 配置 主要配置一些 队列与交换机的关系
 * 简单队列与高级队列与交换机的关系都需要在这里维护
 * Created  on 2022/11/15 14:14:15
 *
 * @author zl
 */
@Configuration
public class AMQPConfig implements RabbitListenerConfigurer {

    @Value("${spring.application.name}")
    private String connectionName;

    //    @Bean
    //    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    //        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    //        var objectMapper = new ObjectMapper();
    //        /*
    //        Jackson2JsonMessageConverter 一个json 序列化转换器，但是只能转换当前程序下的数据 适合严格的数据管理
    //        通过建立idClassMapping 来建立 _typeId_ 与当前程序下Class的转换，

    //         */
    //        var messageConverter = new Jackson2JsonMessageConverter(objectMapper);
    //        messageConverter.setClassMapper(classMapper());
    //        // 主要针对抽象类，例如List abstractObject 忽略_typeId_
    //        messageConverter.setAlwaysConvertToInferredType(true);
    //        factory.setMessageConverter(messageConverter);
    //        factory.setConnectionFactory(connectionFactory);
    //        return factory;
    //    }
    //
    //    @Bean
    //    public ClassMapper classMapper() {
    //        var classMapper = new DefaultJackson2JavaTypeMapper();
    //        classMapper.setTrustedPackages("*"); // 可以设置相信的包， DefaultJackson2JavaTypeMapper  默认只新人 java.util 与Java.lang
    //        var idClassMapping = new HashMap<String, Class<?>>();
    //        idClassMapping.put("org.framework.integration.example.rabbit.producer.entity.CustomMessage", CustomMessage.class);
    //        idClassMapping.put("org.framework.integration.example.rabbit.producer.entity.SubMessage", AbstractMessage.class);
    //        classMapper.setIdClassMapping(idClassMapping);
    //        return classMapper;
    //    }

    //  implements RabbitListenerConfigurer need override follow method

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

    /**
     * 生命连接名称，springboot amqp stater 会自动检测到，并进行设置
     * 例如通过 @ConditionalOnBean(value = ConnectionNameStrategy.class) 进行设置
     *
     * @return 链接名称策略
     */
    @Bean
    public ConnectionNameStrategy connectionNameStrategy() {
        return connectionFactory -> connectionName;
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

    @Bean
    public Queue abstractMessageQueue() {
        return new Queue(QueueDeclare.ABSTRACT_MESSAGE_QUEUE);
    }

    @Bean
    public Queue abstractMessageQueue2() {
        return new Queue(QueueDeclare.ABSTRACT_MESSAGE_QUEUE_2);
    }
}
