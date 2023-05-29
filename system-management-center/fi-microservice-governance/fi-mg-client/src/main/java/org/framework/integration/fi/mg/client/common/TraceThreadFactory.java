package org.framework.integration.fi.mg.client.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created  on 2023/5/29 14:14:07
 *
 * @author zl
 */
public class TraceThreadFactory implements ThreadFactory {

    private final String namePrefix;

    private final AtomicInteger nextWorkerId = new AtomicInteger(1);

    public TraceThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix + "-worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = namePrefix + nextWorkerId.getAndIncrement();
        return new Thread(null, task, name, 0, false);
    }
}
