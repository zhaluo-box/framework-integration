package org.framework.integration.security.core.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created  on 2022/8/1 16:16:33
 *
 * @author zl
 */
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class User {
    String name;

    int age;
}
