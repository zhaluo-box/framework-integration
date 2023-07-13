package org.framework.integration.example.redis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * Created  on 2023/7/10 13:13:53
 *
 * @author wmz
 */
@Data
@Accessors(chain = true)
public class SysDataSegregationGroup {

    private String id;

    @ApiModelProperty("数据隔离策略组名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<SysDataSegregation> dataSegregationList;

    private SysDataSegregation sysDataSegregation;

    /**
     * 逻辑删除字段，基于中粗线，所有数据都要作为逻辑删除，所以采用逻辑删除配置。而且数据库默认类型为tinyint 数据长度为1  默认值为0
     * 0 (false) 1 (true)
     */
    @ApiModelProperty(hidden = true)
    private Byte deleted;

    /**
     * 创建者ID
     */
    private Long createId;

    /**
     * 创建人昵称
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者ID
     */
    private Long updateId;

    /**
     * 更新者昵称
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
