package org.framework.integratio.sys.test.service;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.web.common.security.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created  on 2023/4/27 14:14:53
 *
 * @author zl
 */
@Slf4j
@Service
public class TestService {

    @Autowired
    @Qualifier("traceExecutor")
    private ThreadPoolTaskExecutor executor;

    @Autowired
    @Qualifier("myTraceExecutor")
    private ThreadPoolTaskExecutor myExecutor;

    public void threadTest() {

        log.info("外部日志测试");
        var accountName = SecurityContextHolder.getAccountName();
        Runnable r = () -> log.info("thread test O ! ， name = {}", accountName);

        executor.execute(r);
        Runnable r1 = () -> log.info("thread test M! name = {}", accountName);

        myExecutor.execute(r1);

        Callable<String> cr = () -> {
            log.info("Callable thread test M! name = {}", accountName);
            return accountName;
        };

        var submit = myExecutor.submit(cr);
        try {
            var result = submit.get();
            log.info("result = {}", result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("哈哈哈");
        }

    }

    @Async("myTraceExecutor")
    public void asyncTest() {
        log.info("哈哈");

        System.out.println("SecurityContextHolder.getAccountName() = " + SecurityContextHolder.getAccountName());
    }
}
