package org.framework.integration.example.rabbit.consumer.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created  on 2022/11/24 10:10:46
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public abstract class AbstractMessage {

    private String name;

}
