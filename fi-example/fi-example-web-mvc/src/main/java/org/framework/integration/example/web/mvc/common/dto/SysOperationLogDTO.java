package org.framework.integration.example.web.mvc.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.framework.integration.example.web.mvc.common.entity.SysOperationLog;

/**
 * 系统操作日志
 * Created  on 2023/5/25 10:10:52
 *
 * @author zl
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysOperationLogDTO extends SysOperationLog {
    
}
