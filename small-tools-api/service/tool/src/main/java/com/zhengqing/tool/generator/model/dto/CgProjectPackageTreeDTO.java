package com.zhengqing.tool.generator.model.dto;

import javax.validation.constraints.NotNull;

import com.zhengqing.common.model.dto.BaseDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 项目关联包树查询参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/11/15 12:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("项目关联包树查询参数")
public class CgProjectPackageTreeDTO extends BaseDTO {

    @NotNull(message = "关联项目ID不能为空")
    @ApiModelProperty(value = "项目id")
    private Integer projectId;

}
