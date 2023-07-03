package org.framework.integration.fi.mg.feign.adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.common.service.AbstractSysOperationLogSaveService;
import org.framework.integration.fi.mg.feign.adapter.client.SysOperationLogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/6/30 15:15:24
 *
 * @author zl
 */
@Slf4j
@Component
public class FeignSysOperationLogService extends AbstractSysOperationLogSaveService {

    @Autowired
    @Lazy
    private SysOperationLogClient sysOperationLogClient;

    @Override
    public void save(SysOperationLogDTO sysOperationLogDTO) {

        var response = sysOperationLogClient.add(sysOperationLogDTO);
        if (response.getCode() > 400) {
            log.error("操作日志服务调用异常 ： {}", response.getMessage());
        }

    }
}
