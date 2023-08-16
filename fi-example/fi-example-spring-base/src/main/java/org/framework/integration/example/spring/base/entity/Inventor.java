package org.framework.integration.example.spring.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Created  on 2023/8/16 17:17:16
 *
 * @author zl
 */
@Data
@AllArgsConstructor
public class Inventor {

    private String name;

    private Date birthday;

    private String nationality;
}
