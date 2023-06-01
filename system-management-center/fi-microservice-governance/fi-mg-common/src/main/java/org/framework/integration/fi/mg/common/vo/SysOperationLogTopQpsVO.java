package org.framework.integration.fi.mg.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created  on 2023/5/31 16:16:32
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class SysOperationLogTopQpsVO {

    /**
     * API 分组名称
     */
    private String groupName;

    /**
     * 模块名称 ： 审批模块 （approve-service）
     * 储存使用英文（spring.application.name）
     * 如果显示需要中文， 前端通过字典进行映射
     */
    private String moduleName;

    /**
     * 系统名称： 通常是整个系统的名称。例如 AAA 系统
     */
    private String systemName;

    /**
     * 调用方式过滤 外部调用与内部调用
     */
    private String invokeWay;
}
