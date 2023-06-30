package org.framework.integration.fi.mg.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created  on 2023/6/30 11:11:02
 *
 * @author zl
 */
@FeignClient(path = "", contextId = SysOperationLogClient.CLIENT_NAME, value = "", fallbackFactory = Object.class)
public interface SysOperationLogClient {

    String CLIENT_NAME = "sysOperationLogClient";
}
