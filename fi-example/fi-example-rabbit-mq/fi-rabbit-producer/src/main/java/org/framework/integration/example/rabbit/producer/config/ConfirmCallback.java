package org.framework.integration.example.rabbit.producer.config;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created  on 2022/11/18 17:17:04
 *
 * @author zl
 */
@Data
@Slf4j
@Accessors(chain = true)
public class ConfirmCallback implements ListenableFutureCallback<CorrelationData.Confirm> {

    private AtomicInteger x = new AtomicInteger(0);

    private void setCrd(CorrelationData crd) {
        this.crd = crd;
    }

    protected CorrelationData crd = new CorrelationData("retry - " + UUID.randomUUID());

    private RabbitTemplate rabbitTemplate;

    private String exchange;

    private String routeKey;

    private Object payload;

    private MessagePostProcessor messagePostProcessor;

    @Override
    public void onFailure(Throwable ex) {
        log.info("Failure received {}", ex.getMessage());
        log.info("重试发送");
        do {
            retrySend(this);
            x.getAndAdd(1);
        } while (x.get() < 5);
        throw new RuntimeException("抛出异常，让整个外围事务回滚");
    }

    @Override
    public void onSuccess(CorrelationData.Confirm result) {
        assert result != null;
        if (result.isAck() && crd.getReturned() == null) {
            log.info("消息发送成功");
            return;
        }
        log.info("消息发送失败！return message ： {}", crd.getReturned());
    }

    public void retrySend(ConfirmCallback callback) {
        retrySend(callback.getExchange(), callback.getRouteKey(), callback.getPayload(), callback.getMessagePostProcessor(), callback.getCrd());
    }

    private void retrySend(String exchange, String routeKey, Object payload, MessagePostProcessor messagePostProcessor, CorrelationData crd) {
        rabbitTemplate.convertAndSend(exchange, routeKey, payload, msg -> msg, crd);
        crd.getFuture().addCallback(this);
    }
}

