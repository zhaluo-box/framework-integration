package org.framework.integratio.sys.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.framework.integratio.sys.test.config.LogConstant;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2023/4/20 17:17:09
 *
 * @author zl
 */

@Slf4j
@RestController
@RequestMapping("/root")
public class TestController {

    @GetMapping
    public ResponseEntity<Void> logTest() {
        System.out.println("MDC.getCopyOfContextMap() = " + MDC.getCopyOfContextMap());
        System.out.println("MDC.get(LogConstant.TRACE_ID) = " + MDC.get(LogConstant.TRACE_ID));
        log.info("12312");
        return ResponseEntity.ok();
    }

}
