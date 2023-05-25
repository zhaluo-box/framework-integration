package org.framework.integration.fi.mg.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.framework.integration.fi.mg.common.enums.BusinessType;

import java.util.Date;

/**
 * Created  on 2023/5/24 10:10:02
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class SysOperationLogDTO {

    // --- 基本方法信息 ---

    /**
     * 业务编号： 简称BizCode
     * 通常为UUID 或者 分布式ID（雪花算法.. 衍生算法ID， 具有自增特性，对于检索与存储更加友好）
     */
    private String businessCode;

    /**
     * 操作API name
     */
    private String name;

    /**
     * 业务类型
     */
    private BusinessType businessType;

    /**
     * 请求方法 包含类名信息
     */
    private String method;

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
     * 上游模块名称
     */
    private String fromModuleName;

    /**
     * 调用层级
     */
    private String invokeHierarchy;

    /**
     * 调用方式，是内部调用，还是外部调用
     */
    private String invokeWay;

    // ---------- 原生Http 信息 --------------------

    /**
     * Http请求方式
     */
    private String httpMethod;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestHeaders;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 返回参数
     */
    private String responseData;

    /**
     * 错误消息
     */
    private String errorMsg;

    // ------------操作人的相关信息-----------------

    /**
     * 操作人员ID
     */
    private String operatorId;

    /**
     * 操作人员名称
     */
    private String operatorName;

    /**
     * 操作地址
     */
    private String host;

    /**
     * 地址识别（大致区域 例如 北京，上海）
     */
    private String hostMap;

    /**
     * 操作状态（0正常 1异常）
     */
    private String status;

    /**
     * 操作开始时间（这个时间并不准确，因为是从拦截器中取得的，只能大概代表请求的时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date startTime;

    /**
     * 操作结束时间 （这个时间并不准确，因为是从拦截器中取得的，只能大概代表请求的时间）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date endTime;

    /**
     * 消耗时间
     */
    private Long costTime;

}
