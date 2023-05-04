package org.framework.integration.sys.base.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * Created  on 2023/4/27 11:11:42
 *
 * @author zl
 */
@Aspect
@Component
@Slf4j
public class ScheduledAspect {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void proxyAspect() {
    }

    @Before("proxyAspect()")
    public void before(JoinPoint joinPoint) throws Throwable {
        String traceId = LogTraceIdHelper.getTraceId();
        MDC.put(LogConstant.TRACE_ID, traceId);
    }
}
