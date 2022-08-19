package org.framework.integration.sys.base.controller;

import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.web.common.security.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2022/8/19 15:15:21
 *
 * @author zl
 */
@RestController
@RequestMapping("/accounts/")
public class AccountController {

    @GetMapping
    public ResponseEntity<String> test() {
        System.out.println(SecurityContextHolder.getAuthInfo());
        System.out.println("测试----");
        return ResponseEntity.ok("测试");
    }

}
