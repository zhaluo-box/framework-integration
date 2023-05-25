package org.framework.integration.example.web.mvc.controller;

import org.framework.integration.example.web.mvc.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created  on 2023/5/23 16:16:35
 *
 * @author zl
 */
@RestController
@RequestMapping("/operation-logs")
public class OperationLogTestController {

    @Autowired
    private AsyncService asyncService;

    @PostMapping("actions/simple-body-test")
    public Map<String, Map<String, Object>> simpleBodyTest(@RequestBody Map<String, Map<String, Object>> param) {
        asyncService.AsyncOperationLogTest(param);
        return param;
    }
}
