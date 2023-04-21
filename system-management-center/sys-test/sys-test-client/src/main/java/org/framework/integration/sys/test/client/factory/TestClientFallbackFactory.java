package org.framework.integration.sys.test.client.factory;

import lombok.extern.slf4j.Slf4j;
import org.framework.integration.common.core.http.response.ResponseEntity;
import org.framework.integration.sys.test.client.TestClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/4/20 17:17:21
 *
 * @author zl
 */
@Slf4j
@Component
public class TestClientFallbackFactory implements FallbackFactory<TestClient> {

    @Override
    public TestClient create(Throwable cause) {
        log.error("测试服务调用出错 ： {}", cause.getMessage());
        return new TestClient() {
            @Override
            public ResponseEntity<Void> logTest() {
                log.info("xxxxxxxxxxxxx");
                return ResponseEntity.fail("log test failed" + cause.getMessage());
            }
        };
    }
}
