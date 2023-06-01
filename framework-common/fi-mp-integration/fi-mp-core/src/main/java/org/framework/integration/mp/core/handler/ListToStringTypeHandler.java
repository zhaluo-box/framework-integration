package org.framework.integration.mp.core.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 待优化， 基于泛型 支持多种基础包装类 不支持自定义类型 自定义类型推荐直接使用jsonTypeHandler [go,jack,fast]
 * 不加MappedType与MappedJdbcTypes 注解也可以正确映射，但是添加自定义typeHandler的地方，需要在@TableName 的属性 autoResult=true
 * Created  on 2022/3/14 15:15:01
 *
 * @author zl
 */
public class ListToStringTypeHandler extends AbstractJsonTypeHandler<List<Long>> {
    @Override
    public List<Long> parse(String json) {
        List<Long> list = Collections.emptyList();
        if (StringUtils.isBlank(json)) {
            return list;
        }
        String[] split = json.split(",");
        return Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
    }

    @Override
    public String toJson(List<Long> obj) {
        return obj.stream().map(Object::toString).collect(Collectors.joining(","));
    }

}
