package org.framework.integration.fi.mg.common.service;

/**
 * 业务追踪ID
 * Created  on 2023/5/25 14:14:36
 *
 * @author zl
 */
public interface BusinessIdProvider {

    default String getBusinessCode() {
        return null;
    }
}



