package org.framework.integration.fi.mg.feign.adapter.client;

import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.feign.adapter.factory.SysOperationLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created  on 2023/6/30 14:14:53
 *
 * @author zl
 */
// TODO  2023/6/30 contextId 支持SPEL 表达式 后期应修改
@FeignClient(path = "sysOperationLog", value = SysOperationLogClient.CLIENT_NAME, contextId = "fi-mg", fallbackFactory = SysOperationLogFallbackFactory.class)
public interface SysOperationLogClient {

    String CLIENT_NAME = "sysOperationLogClient";

    @PostMapping
    ResponseEntity<Void> add(@RequestBody SysOperationLogDTO sysOperationLogDTO);
}
