package org.framework.integration.fi.mg.client.bootstrap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.framework.integration.fi.mg.common.annotations.Log;
import org.framework.integration.fi.mg.common.annotations.LogGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created  on 2023/5/23 15:15:26
 *
 * @author zl
 */
@Slf4j
@Component
public class OperationLogAuditBootstrapService implements BootstrapService {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private static final List<String> EXCLUDE_CLASS_LIST = List.of("");

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("开始检查controller中的日志注解");

        List<Method> methodList = handlerMapping.getHandlerMethods().values().stream().map(HandlerMethod::getMethod).collect(Collectors.toList());

        methodList.stream().filter(m -> !EXCLUDE_CLASS_LIST.contains(m.getDeclaringClass().getSimpleName())).forEach(m -> {
            Assert.isTrue(m.getDeclaringClass().isAnnotationPresent(LogGroup.class), m.getDeclaringClass() + "的类缺少logGroup注释");
            Assert.isTrue(m.isAnnotationPresent(Log.class), MethodUtil.getMethodDescription(m) + "的方法缺少log注释");
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
}
