package org.framework.integration.example.redis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created  on 2023/7/10 14:14:29
 *
 * @author wmz
 */
@Data
@Accessors(chain = true)
@ApiModel("数据隔离策略")
public class SysDataSegregation {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("客户类型")
    private List<String> customerTypes;

    @ApiModelProperty("区域")
    private List<Long> areas;

    @ApiModelProperty("产品线")
    private List<String> productLine;

    @ApiModelProperty("产品类型")
    private List<String> productTypes;

}
