package org.framework.integration.example.rabbit.producer.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created  on 2022/11/16 16:16:11
 *
 * @author zl
 */
@Data
public class CustomMessage {

    private String name;

    private BigDecimal weight;

    private int age;

}
