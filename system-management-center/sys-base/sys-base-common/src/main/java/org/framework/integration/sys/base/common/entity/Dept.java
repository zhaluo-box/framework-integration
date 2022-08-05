package org.framework.integration.sys.base.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统部门信息
 * Created  on 2022/8/2 11:11:14
 *
 * @author zl
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dept {
}
