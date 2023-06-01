package org.framework.integration.fi.mg.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一天 24 小时QPS 详情
 * Created  on 2023/5/31 16:16:03
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class SysOperationLogDayQpsDTO {

    /**
     * 时间
     */
    private String time;

    /**
     * name = moduleName/groupName/name
     */
    private String name;

    /**
     * 数量
     */
    private Integer size;

}
