package org.framework.integration.example.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created  on 2022/9/22 11:11:05
 *
 * @author zl
 */
@Data
@EqualsAndHashCode
public class BaseMessage<T> {

    private String source;

    private String target;

    private T data;

}
