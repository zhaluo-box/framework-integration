package org.framework.integratio.sys.test.threadpool;

import org.slf4j.MDC;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created  on 2023/4/27 14:14:39
 *
 * @author zl
 */
public class TraceThreadFactory implements ThreadFactory {

    private final String name;

    private final AtomicLong tag = new AtomicLong(0);

    public TraceThreadFactory(String name) {
        super();
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        var copyOfContextMap = MDC.getCopyOfContextMap();

        Runnable proxy = () -> {
            MDC.setContextMap(copyOfContextMap);
            r.run();
        };

        var thread = new Thread(proxy);
        thread.setName(name + "-" + tag.getAndIncrement());
        return thread;
    }
}
