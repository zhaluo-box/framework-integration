package org.framework.integration.sys.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.sys.test.client.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2023/1/31 12:12:23
 *
 * @author zl
 */
@RestController
@Slf4j
@RequestMapping("/logging-level-test/")
@Api(tags = "日志等级测试")
public class LoggingLevelTestController {

    @Autowired
    private TestClient testClient;

    /**
     * TRACE < DEBUG < INFO < WARN < ERROR
     */
    @GetMapping
    public ResponseEntity<Void> DyncLogging() {
        log.trace("trace log info test");
        log.debug("debug log info test");
        log.info("info log info test");
        log.warn("warn log info test");
        log.error("error log info test");
        return ResponseEntity.ok();
    }

    @GetMapping("actions/log-trace-id-test")
    @ApiOperation("日志traceId 测试")
    public ResponseEntity<Void> logTraceIdTest() {
        log.info("base log trace info");
        testClient.logTest();
        return ResponseEntity.ok();
    }
}
