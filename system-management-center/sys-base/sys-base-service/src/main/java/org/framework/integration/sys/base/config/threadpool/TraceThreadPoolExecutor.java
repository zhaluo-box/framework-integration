package org.framework.integration.sys.base.config.threadpool;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created  on 2023/4/27 14:14:28
 *
 * @author zl
 */
public class TraceThreadPoolExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadPoolWrapper.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadPoolWrapper.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadPoolWrapper.wrap(task, MDC.getCopyOfContextMap()));
    }
}
