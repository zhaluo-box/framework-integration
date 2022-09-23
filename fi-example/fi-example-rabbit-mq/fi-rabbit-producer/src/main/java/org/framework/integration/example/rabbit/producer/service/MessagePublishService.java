package org.framework.integration.example.rabbit.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created  on 2022/9/23 15:15:06
 *
 * @author zl
 */
@Slf4j
@Service
public class MessagePublishService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void publishSimpleQueue() {

    }
}
