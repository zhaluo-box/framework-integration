package org.framework.integration.fi.mg.common.annotations;

import org.framework.integration.fi.mg.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 操作日志
 * Created  on 2023/5/23 14:14:51
 *
 * @author zl
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    /**
     * 名称
     */
    String name() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 排除指定的请求参数
     */
    String[] excludeParamNames() default {};
}
