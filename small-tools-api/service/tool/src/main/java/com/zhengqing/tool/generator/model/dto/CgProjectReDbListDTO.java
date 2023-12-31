package com.zhengqing.tool.generator.model.dto;

import com.zhengqing.common.base.model.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 代码生成器 - 项目关联数据库表查询参数
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020-11-14 13:55:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成器 - 项目关联数据库表查询参数")
public class CgProjectReDbListDTO extends BaseDTO {

    @ApiModelProperty(value = "所属项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "数据源id")
    private Integer dbDataSourceId;

}
