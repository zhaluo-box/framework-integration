package org.framework.integration.fi.mg.common.service;

import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;
import org.framework.integration.fi.mg.common.dto.SysOperationLogOriginalDTO;

/**
 * 只用作保存的service
 * Created  on 2023/5/25 11:11:40
 *
 * @author zl
 */
public interface SysOperationLogSaveService {

    void save(SysOperationLogDTO sysOperationLogDTO);

    default void saveBefore(SysOperationLogDTO sysOperationLogDTO) {
        // do nothing
    }

    SysOperationLogDTO transformer(SysOperationLogOriginalDTO sysOperationLogOriginalDTO);

}
