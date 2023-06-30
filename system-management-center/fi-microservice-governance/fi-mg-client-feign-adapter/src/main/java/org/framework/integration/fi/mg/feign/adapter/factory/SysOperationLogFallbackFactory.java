package org.framework.integration.fi.mg.feign.adapter.factory;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.feign.adapter.client.SysOperationLogClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/6/30 14:14:56
 *
 * @author zl
 */
@Slf4j
@Component
public class SysOperationLogFallbackFactory implements FallbackFactory<SysOperationLogClient> {

    @Override
    public SysOperationLogClient create(Throwable cause) {
        log.error("FI-MG 操作日志服务调用失败", cause);
        return new SysOperationLogClient() {
            @Override
            public ResponseEntity<Void> add(SysOperationLogDTO sysOperationLogDTO) {
                return ResponseEntity.fail("fi-mg 操作日志服务新增调用失败！" + cause.getMessage());
            }
        };
    }
}
