package org.framework.integration.example.i18n.service;

import org.framework.integration.example.i18n.utils.MessageUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created  on 2023/5/16 16:16:24
 *
 * @author zl
 */

@Service
public class AsyncService {

    @Async
    public void printName(String code) {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("MessageUtil.getMessage(code, null) = " + MessageUtil.getMessage(code, null));

        System.out.println("new Date() = " + new Date());
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("new Date() = " + new Date());
    }
}
