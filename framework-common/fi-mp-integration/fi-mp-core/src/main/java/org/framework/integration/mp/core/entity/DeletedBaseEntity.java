package org.framework.integration.mp.core.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created  on 2023/3/16 14:14:18
 *
 * @author zl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeletedBaseEntity extends BaseEntity {

    /**
     * 逻辑删除字段，基于中粗线，所有数据都要作为逻辑删除，所以采用逻辑删除配置。而且数据库默认类型为tinyint 数据长度为1  默认值为0
     * 0 (false) 1 (true)
     */
    @TableLogic
    private Byte deleted;
}
