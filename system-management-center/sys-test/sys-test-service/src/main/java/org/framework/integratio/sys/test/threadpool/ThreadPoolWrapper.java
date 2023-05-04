package org.framework.integratio.sys.test.threadpool;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created  on 2023/4/27 14:14:31
 *
 * @author zl
 */
public class ThreadPoolWrapper {

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
