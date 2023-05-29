package org.framework.integration.fi.mg.client.config.support;

import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 起个名字真特么难
 * Created  on 2023/5/26 15:15:32
 *
 * @author zl
 */
public class LogApplicationContextHolder {

    private static final Map<Method, HandlerMethodInfo> METHOD_CACHE = new HashMap<>(256);

    public static void register(HandlerMethodInfo handlerMethodInfo) {
        METHOD_CACHE.put(handlerMethodInfo.getHandlerMethod(), handlerMethodInfo);
    }

    public static Map<Method, HandlerMethodInfo> getMethodCache() {
        return METHOD_CACHE;
    }

    /**
     * @param method handlerMethod 处理器
     * @return 是否包含当前方法处理器
     */
    public static boolean hasMethod(Method method) {
        return METHOD_CACHE.containsKey(method);
    }

    public static HandlerMethodInfo getHandleMethodInfo(Method method) {
        return METHOD_CACHE.get(method);
    }

}
