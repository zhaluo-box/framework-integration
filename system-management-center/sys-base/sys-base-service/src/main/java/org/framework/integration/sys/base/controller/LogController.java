package org.framework.integration.sys.base.controller;

import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.sys.base.common.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2022/7/29 11:11:08
 *
 * @author zl
 */
@RestController
@RequestMapping("/log/")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("actions/login/")
    public ResponseEntity<String> login(String account, String password) {
        return ResponseEntity.ok(logService.login(account, password), "登录成功");
    }

    @PostMapping("actions/logout/")
    public ResponseEntity<Void> logout(String account) {
        logService.logout(account);
        return ResponseEntity.ok();
    }

}
