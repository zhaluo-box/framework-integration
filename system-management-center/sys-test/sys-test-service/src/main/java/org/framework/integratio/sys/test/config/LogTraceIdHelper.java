package org.framework.integratio.sys.test.config;

import java.util.UUID;

/**
 * Created  on 2023/4/27 11:11:47
 *
 * @author zl
 */
public class LogTraceIdHelper {

    public static String getTraceId() {
        return UUID.randomUUID().toString();
    }

}
