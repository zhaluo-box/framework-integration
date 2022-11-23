package org.framework.integration.example.rabbit.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.common.dto.BaseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created  on 2022/9/23 15:15:06
 *
 * @author zl
 */
@Slf4j
@Service
public class MessagePublishService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void txView(BaseMessage<String> message) {
        log.info("rabbit mq 事务是否开启 : {}", rabbitTemplate.isChannelTransacted());
        rabbitTemplate.convertAndSend(message.getSource(), message.getTarget(), message.getData());
        var x = Integer.parseInt(message.getData()) % 2;
        if (x != 0) {
            throw new RuntimeException("事务测试@1");
        }
    }
}
