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

    /**
     * profile> 服务名称.yml > 服务名称（没有后缀） > extension > shared
     * [BootstrapPropertySource {name='bootstrapProperties-example-nacos-dev.yml,DEFAULT_GROUP'},
     * BootstrapPropertySource {name='bootstrapProperties-example-nacos.yml,DEFAULT_GROUP'},
     * BootstrapPropertySource {name='bootstrapProperties-example-nacos,DEFAULT_GROUP'},
     * BootstrapPropertySource {name='bootstrapProperties-extension-nacos-test-config2.yml,DEFAULT_GROUP'},
     * BootstrapPropertySource {name='bootstrapProperties-extension-nacos-test-config1.yml,DEFAULT_GROUP'},
     * BootstrapPropertySource {name='bootstrapProperties-shared-nacos-test-config2.yml,DEFAULT_GROUP'},
     * BootstrapPropertySource {name='bootstrapProperties-shared-nacos-test-config1.yml,DEFAULT_GROUP'}]
     */
    @GetMapping("actions/config-lifecycle/")
    @ApiOperation("测试nacos 配置文件生效顺序")
    public ResponseEntity<String> configLifecycle() {
        return ResponseEntity.ok(value);
    }
}
