package org.framework.integration.example.nacos.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2022/9/30 14:14:34
 *
 * @author zl
 */
@RefreshScope
@RestController
@RequestMapping("/nacos-config/")
public class NacosConfigController {

    @Value("${test.nacos.properties.lifecycle}")
    private String value;

    @GetMapping("actions/config-lifecycle/")
    @ApiOperation("测试nacos 配置文件生效顺序")
    public ResponseEntity<String> configLifecycle() {
        return ResponseEntity.ok(value);
    }
}
