package org.framework.integration.fi.mg.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created  on 2023/5/31 16:16:10
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class SysOperationLogPageVO {

    /**
     * 基于traceId 进行精确查询
     */
    private String businessCode;

    /**
     * full_name = systemName/moduleName/groupName/name
     * 操作API name [@log(name="")]
     */
    private String name;

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

    /**
     * 操作人员ID
     */
    private String operatorId;

    /**
     * 查询起始时间
     */
    private Date startTime;

    /**
     * 查询结束时间
     */
    private Date endTime;

}
