package org.framework.integratio.sys.test.config;

import java.util.UUID;

/**
 * Created  on 2023/4/27 11:11:48
 *
 * @author zl
 */
public interface TraceIdGenerateService {

    default String getId() {
        return UUID.randomUUID().toString();
    }

}
