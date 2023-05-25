package org.framework.integration.fi.mg.common.service;

/**
 * 操作人信息提供service
 * Created  on 2023/5/25 15:15:47
 *
 * @author zl
 */
public interface SysOperatorProvider {

    /**
     * @return 获取操作员ID
     */
    String getOperatorId();

    /**
     * @return 获取操作员名称
     */
    String getOperatorName();

}
