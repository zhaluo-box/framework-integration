package org.framework.integration.example.rabbit.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * AMQP 配置 主要配置一些 队列与交换机的关系
 * 简单队列与高级队列与交换机的关系都需要在这里维护
 * Created  on 2022/11/15 14:14:15
 *
 * @author zl
 */
@Slf4j
@Configuration
public class AMQPConfig {

    @Value("${spring.application.name}")
    private String connectionName;

    /**
     * 支持路由的连接工厂
     * {@link <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#routing-connection-factory">路由连接工厂</href>}
     */
    // @Bean
    public ConnectionFactory routeConnectionFactory() {
        var routingConnectionFactory = new SimpleRoutingConnectionFactory();
        var routeConnections = new HashMap<Object, ConnectionFactory>();
        routeConnections.put("", null);
        routingConnectionFactory.setTargetConnectionFactories(routeConnections);
        return null;
    }

    /**
     * spring:
     * rabbitmq:
     * virtual-host: fi-example
     * username: fi
     * password: 123456
     * #  publisher-confirm-type: correlated
     * #  publisher-returns: true
     * #  template:
     * #    mandatory: true
     * confirm 模式与 事务模式冲突，需要注意yml中的配置
     * {@link <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#json-message-converter">Jackson2JsonMessageConverter</a>}
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        var messageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(messageConverter);

        // messageConverter.setClassMapper(); 支持指定class 集合(idClassMapping ) 支持指定默认type  @see DefaultClassMapper
        // rabbitTemplate.setMandatory(true); 支持yml配置 查看nacos

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("消息路由失败回调！");
            log.info("return callback : {}", returnedMessage);
        });

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("消息到达交换机触发回调！");
            log.info("confirm callback correlationData : {}", correlationData);
            log.info("confirm callback ack : {} ", ack);
            log.info("confirm callback cause : {} ", cause);
        });

        // 开启事务通道
        rabbitTemplate.setChannelTransacted(true);

        return rabbitTemplate;
    }

    /**
     * rabbit MQ 开启事务
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
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
}
