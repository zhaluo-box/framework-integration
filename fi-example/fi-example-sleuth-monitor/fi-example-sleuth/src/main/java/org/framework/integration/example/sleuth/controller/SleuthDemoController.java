package org.framework.integration.example.sleuth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2023/5/19 15:15:39
 *
 * @author zl
 */
@Slf4j
@RestController
@RequestMapping("/sleuth-demos")
public class SleuthDemoController {

    @GetMapping("actions/print-logs")
    public void printLog() {
        log.info("test log info!");
    }

}
