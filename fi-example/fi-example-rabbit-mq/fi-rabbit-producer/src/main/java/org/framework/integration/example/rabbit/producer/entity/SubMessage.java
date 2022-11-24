package org.framework.integration.example.rabbit.producer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created  on 2022/11/24 10:10:47
 *
 * @author zl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SubMessage extends AbstractMessage {

    private String payload;

}
