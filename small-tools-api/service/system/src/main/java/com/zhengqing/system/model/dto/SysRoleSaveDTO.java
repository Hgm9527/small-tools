package com.zhengqing.system.model.dto;

import com.zhengqing.common.validator.fieldrepeat.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 * 保存角色信息传入参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/10 15:01
 */
@Data
@ApiModel("保存角色信息传入参数")
public class SysRoleSaveDTO {

    @NotNull(message = "角色id不能为空！", groups = Update.class)
    @ApiModelProperty(value = "主键ID")
    private Integer roleId;

    @NotBlank(message = "角色名不能为空！")
    @ApiModelProperty(value = "角色名")
    private String name;

    @NotBlank(message = "角色编号不能为空！")
    @ApiModelProperty(value = "角色编号")
    private String code;

    @ApiModelProperty(value = "状态(1:开启 0:禁用)")
    private Integer status;

}
