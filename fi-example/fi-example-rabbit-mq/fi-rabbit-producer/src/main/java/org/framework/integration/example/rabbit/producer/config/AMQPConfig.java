package org.framework.integration.example.rabbit.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
@Slf4j
@Configuration
public class AMQPConfig {

    @Value("${spring.application.name}")
    private String connectionName;

    /**
     * {@link <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#json-message-converter">Jackson2JsonMessageConverter</a>}
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        var messageConverter = new Jackson2JsonMessageConverter();
        //        messageConverter.setClassMapper(); 支持指定class 集合(idClassMapping ) 支持指定默认type  @see DefaultClassMapper
        rabbitTemplate.setMessageConverter(messageConverter);
        //        rabbitTemplate.setMandatory(true); 支持yml配置 查看nacos
        rabbitTemplate.setReturnsCallback(returnedMessage -> log.info("return callback : {}", returnedMessage));
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("confirm callback correlationData : {}", correlationData);
            log.info("confirm callback ack : {} ", ack);
            log.info("confirm callback cause : {} ", cause);
        });
        return rabbitTemplate;
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
