package org.framework.integration.fi.mg.client.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.framework.integration.fi.mg.common.enums.BusinessType;

import java.lang.reflect.Method;

/**
 * Created  on 2023/5/26 15:15:37
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class HandlerMethodInfo {

    /**
     * 方法
     */
    private Method handlerMethod;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法声明的class
     */
    private Class<?> targetClass;

    /**
     * 方法声明的class 名称
     */
    private String targetClassName;

    /**
     * 方法签名
     */
    private String methodSign;

    // -----------------注解元信息 ---------------

    /**
     * 业务分组名称
     * -@LogGroup(name="")
     */
    private String groupName;

    /**
     * API 名称
     * -@Log(name="")
     */
    private String name;

    /**
     * 业务操作类型
     */
    private BusinessType businessType;

    /**
     * 需要忽略的参数
     */
    private String[] excludeParam;

}
