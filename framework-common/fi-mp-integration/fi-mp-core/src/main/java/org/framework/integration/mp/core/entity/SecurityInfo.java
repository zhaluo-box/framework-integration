package org.framework.integration.mp.core.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created  on 2023/5/30 15:15:23
 *
 * @author zl
 */
@Data
@Accessors(chain = true)
public class SecurityInfo {

    private String userId;

    private String userName;

}
