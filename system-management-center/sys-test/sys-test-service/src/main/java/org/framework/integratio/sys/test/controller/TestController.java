package org.framework.integratio.sys.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public ResponseEntity<Void> logTest() {
    
        log.info("12312");
        if (throwFlag.getAndSet(false)) {
            throw new RuntimeException("xxxxx");
        } else {
            throwFlag.set(true);
        }
        return ResponseEntity.ok();
    }

}
