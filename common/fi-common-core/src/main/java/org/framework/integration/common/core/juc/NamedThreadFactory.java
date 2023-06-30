package org.framework.integration.common.core.juc;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created  on 2023/5/29 14:14:07
 *
 * @author zl
 */
public class NamedThreadFactory implements ThreadFactory {

    private final String namePrefix;

    private final AtomicInteger nextWorkerId = new AtomicInteger(1);

    public NamedThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix + "-worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = namePrefix + nextWorkerId.getAndIncrement();
        return new Thread(null, task, name, 0, false);
    }
}
