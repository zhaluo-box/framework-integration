package org.framework.integration.example.web.mvc.common.annotations;

import java.lang.annotation.*;

/**
 * Created  on 2023/5/23 14:14:53
 *
 * @author zl
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface LogGroup {

    /**
     * @return 操作日志分组名称
     */
    String name() default "";

    /**
     * @return 操作日志分组
     */
    String desc() default "";
}
