package org.framework.integration.sys.test.client;

import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.sys.test.client.factory.TestClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created  on 2023/4/20 17:17:20
 *
 * @author zl
 */
@FeignClient(path = "/root", contextId = TestClient.CLIENT_NAME, value = "sys-test", fallbackFactory = TestClientFallbackFactory.class)
public interface TestClient {

    String CLIENT_NAME = "testClient";

    @GetMapping
    ResponseEntity<Void> logTest();
}
