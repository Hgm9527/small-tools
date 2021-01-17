package com.zhengqing.tool.generator.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zhengqing.common.model.dto.BaseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目关联数据库具体表信息查询参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/14 22:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("项目关联数据库具体表信息查询参数")
public class CgProjectReDbTableInfoDTO extends BaseDTO {

    @NotNull(message = "数据源id不能为空")
    @ApiModelProperty(value = "数据源ID")
    private Integer projectReDbDataSourceId;

    @NotBlank(message = "数据库表名不能为空！")
    @ApiModelProperty(value = "数据库表名")
    private String tableName;

}
