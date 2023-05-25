package org.framework.integration.fi.mg.common.enums;

/**
 * 业务操作类型
 * Created  on 2023/5/23 15:15:00
 *
 * @author zl
 */
public enum BusinessType {

    /**
     * 其它
     */
    OTHER,

    /**
     * 新增添加
     */
    ADD,

    /**
     * 编辑（新增与保存都有的业务行为，直接使用edit）
     */
    EDIT,

    /**
     * 删除
     */
    DELETE,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 报表&BI
     */
    REPORT

}
