package org.framework.integration.example.rabbit.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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
     *  <a href="https://docs.spring.io/spring-amqp/docs/current/reference/html/#routing-connection-factory">路由连接工厂</href>
     */
    // @Bean
    public ConnectionFactory routeConnectionFactory() {
        var routingConnectionFactory = new SimpleRoutingConnectionFactory();
        var routeConnections = new HashMap<Object, ConnectionFactory>();
        routeConnections.put("", null);
        routingConnectionFactory.setTargetConnectionFactories(routeConnections);
        return null;
    }

    @Bean("batchQueueTaskScheduler")
    public TaskScheduler batchQueueTaskScheduler() {
        TaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        return taskScheduler;
    }

    //批量处理rabbitTemplate
    @Bean
    public BatchingRabbitTemplate batchQueueRabbitTemplate(ConnectionFactory connectionFactory,
                                                           @Qualifier("batchQueueTaskScheduler") TaskScheduler taskScheduler) {

        //!!!重点： 所谓批量， 就是spring 将多条message重新组成一条message, 发送到mq, 从mq接受到这条message后，在重新解析成多条message

        //一次批量的数量
        int batchSize = 10;
        // 缓存大小限制,单位字节，
        // simpleBatchingStrategy的策略，是判断message数量是否超过batchSize限制或者message的大小是否超过缓存限制，
        // 缓存限制，主要用于限制"组装后的一条消息的大小"
        // 如果主要通过数量来做批量("打包"成一条消息), 缓存设置大点
        // 详细逻辑请看simpleBatchingStrategy#addToBatch()
        int bufferLimit = 1024; //1 K
        long timeout = 10000;

        //注意，该策略只支持一个exchange/routingKey
        //A simple batching strategy that supports only one exchange/routingKey
        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);
        var batchingRabbitTemplate = new BatchingRabbitTemplate(connectionFactory, batchingStrategy, taskScheduler);
        batchingRabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return batchingRabbitTemplate;
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
