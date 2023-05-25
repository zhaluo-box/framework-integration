package org.framework.integration.fi.mg.common.service;

import org.springframework.core.Ordered;
import org.springframework.http.HttpRequest;

/**
 * Created  on 2023/5/25 11:11:45
 *
 * @author zl
 */
public interface SysOperationLogFilter extends Ordered {

    boolean filter(HttpRequest request);

}
