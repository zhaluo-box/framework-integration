package org.framework.integratio.sys.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.framework.integratio.sys.test.service.TestService;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.web.common.security.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created  on 2023/4/20 17:17:09
 *
 * @author zl
 */

@Slf4j
@RestController
@RequestMapping("/root")
public class TestController {

    static final AtomicBoolean throwFlag = new AtomicBoolean(true);

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    @Autowired
    private TestService testService;

    @GetMapping("actions/log-test")
    public ResponseEntity<Void> logTest() {

        log.info("12312");
        if (throwFlag.getAndSet(false)) {
            throw new RuntimeException("xxxxx");
        } else {
            throwFlag.set(true);
        }
        return ResponseEntity.ok();
    }

    @GetMapping("actions/ttl-test")
    public ResponseEntity<String> ttlTest() {
        var accountName = SecurityContextHolder.getAccountName();
        EXECUTOR.execute(() -> System.out.println("ttl test" + SecurityContextHolder.getAccountName()));
        return ResponseEntity.ok(accountName);
    }

    @GetMapping("actions/")
    public ResponseEntity<String> threadTest() {
        testService.threadTest();
        return ResponseEntity.ok("");
    }

    @GetMapping("actions/async-test")
    public ResponseEntity<Void> asyncTest() {
        log.info("嘿嘿嘿");
        testService.asyncTest();
        return ResponseEntity.ok();
    }

}
