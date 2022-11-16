package org.framework.integration.example.rabbit.consumer.config;

import org.framework.integration.example.common.constant.ExchangeDeclare;
import org.framework.integration.example.common.constant.QueueDeclare;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AMQP 配置 主要配置一些 队列与交换机的关系
 * 简单队列与高级队列与交换机的关系都需要在这里维护
 * Created  on 2022/11/15 14:14:15
 *
 * @author zl
 */
@Configuration
public class AMQPConfig {

    @Value("${spring.application.name}")
    private String connectionName;

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
