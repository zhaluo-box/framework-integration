package org.framework.integration.fi.mg.client.bootstrap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.client.common.HandlerMethodInfo;
import org.framework.integration.fi.mg.client.config.properties.MGSysOperationLogConfigProperties;
import org.framework.integration.fi.mg.client.config.support.LogApplicationContextHolder;
import org.framework.integration.fi.mg.common.annotations.Log;
import org.framework.integration.fi.mg.common.annotations.LogGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created  on 2023/5/23 15:15:26
 *
 * @author zl
 */
@Slf4j
public class OperationLogInitBootstrapService implements BootstrapService {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private MGSysOperationLogConfigProperties mgSysOperationLogConfigProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("开始检查controller中的日志注解");

        List<String> ignoreClass = getIgnoreClass();

        List<Method> methodList = handlerMapping.getHandlerMethods().values().stream().map(HandlerMethod::getMethod).collect(Collectors.toList());

        methodList.stream().filter(m -> !ignoreClass.contains(m.getDeclaringClass().getSimpleName())).peek(m -> {
            Assert.isTrue(m.getDeclaringClass().isAnnotationPresent(LogGroup.class), m.getDeclaringClass() + "的类缺少logGroup注释");
            Assert.isTrue(m.isAnnotationPresent(Log.class), MethodUtil.getMethodDescription(m) + "的方法缺少log注释");
        }).forEach(method -> {

            HandlerMethodInfo handlerMethodInfo = new HandlerMethodInfo();
            Log logAnnotation = method.getAnnotation(Log.class);
            if (logAnnotation.ignore()) {
                return;
            }
            Class<?> targetClass = method.getDeclaringClass();
            LogGroup logGroup = targetClass.getDeclaredAnnotation(LogGroup.class);
            handlerMethodInfo.setHandlerMethod(method)
                             .setTargetClass(targetClass)
                             .setTargetClassName(targetClass.getName())
                             .setName(logAnnotation.name())
                             .setBusinessType(logAnnotation.businessType())
                             .setExcludeParam(logAnnotation.excludeParamNames())
                             .setGroupName(logGroup.name());

            if (log.isTraceEnabled()) {
                log.trace(" method name : {}, methodHandlerInfo : {}", method.getName(), handlerMethodInfo);
            }

            LogApplicationContextHolder.register(handlerMethodInfo);
        });

        log.info("日志注解检查完成");
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MethodUtil {

        public static String getMethodDescription(Method method) {
            var args = Stream.of(method.getParameters()).map(p -> p.getType().getName()).collect(Collectors.joining(","));
            return String.format("%s.%s(%s)", method.getDeclaringClass().getName(), method.getName(), args);
        }
    }

    private List<String> getIgnoreClass() {
        List<String> result = new ArrayList<>();

        MGSysOperationLogConfigProperties.LogConfigProperties configProperties;
        if (Objects.nonNull(configProperties = mgSysOperationLogConfigProperties.getGlobalConfig())) {
            result.addAll(configProperties.getIgnoreClass());
        }

        if (Objects.nonNull(configProperties = mgSysOperationLogConfigProperties.getLocalConfig())) {
            result.addAll(configProperties.getIgnoreClass());
        }

        return result;
    }
}