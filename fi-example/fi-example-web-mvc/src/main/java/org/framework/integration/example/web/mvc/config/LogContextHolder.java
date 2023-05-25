package org.framework.integration.example.web.mvc.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created  on 2023/5/23 16:16:10
 *
 * @author zl
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogContextHolder {

    //    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new NamedThreadLocal<>("operation-log-context");
    private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static Map<String, Object> getLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void setData(String key, Object value) {
        var localMap = getLocalMap();
        localMap.put(key, value);
    }

    public static Object getData(String key) {
        return getLocalMap().get(key);
    }

}
