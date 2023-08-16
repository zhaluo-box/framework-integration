package org.framework.integration.example.spring.base.controller;

import org.framework.integration.example.spring.base.service.SPELService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主要用来测试SPEL
 * Created  on 2023/8/16 16:16:24
 *
 * @author zl
 */
@RestController
@RequestMapping("spel")
public class SPELController {

    @Autowired
    private SPELService spelService;

    @GetMapping("parse")
    public ResponseEntity<String> parse() {
        return ResponseEntity.ok(spelService.simpleParse());
    }
}
