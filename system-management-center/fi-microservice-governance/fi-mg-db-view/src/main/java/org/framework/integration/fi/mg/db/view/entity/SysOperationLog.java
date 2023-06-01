package org.framework.integration.fi.mg.db.view.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.framework.integration.fi.mg.common.dto.SysOperationLogDTO;

/**
 * Created  on 2023/5/30 11:11:50
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true)
public class SysOperationLog extends SysOperationLogDTO {

    @TableId
    private Long id;

    /**
     *
     * TODO 2023/5/31 还需要一些维度字段  年月、日、时、分、秒
     */
}
