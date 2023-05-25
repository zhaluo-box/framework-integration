package org.framework.integration.example.web.mvc.service;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.example.web.mvc.config.LogContextHolder;
import org.framework.integration.example.web.mvc.utils.MessageUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created  on 2023/5/16 16:16:24
 *
 * @author zl
 */
@Slf4j
@Service
@SuppressWarnings("all")
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

    @Async
    public void AsyncOperationLogTest(Map<String, Map<String, Object>> param) {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println(param);

        System.out.println("new Date() = " + new Date());
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.error("AsyncOperationLogTest log-context print : {}", LogContextHolder.getLocalMap());

        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("new Date() = " + new Date());
    }

}
