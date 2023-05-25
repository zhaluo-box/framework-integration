package org.framework.integration.fi.mg.common.service;

import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;

/**
 * 只用作保存的service
 * Created  on 2023/5/25 11:11:40
 *
 * @author zl
 */
public interface SysOperationLogSaveService {

    void save(SysOperationLogDTO sysOperationLogDTO);

    void saveBefore(SysOperationLogDTO sysOperationLogDTO);

    default void handleLog(SysOperationLogDTO sysOperationLogDTO) {
        saveBefore(sysOperationLogDTO);
        save(sysOperationLogDTO);
    }
}
