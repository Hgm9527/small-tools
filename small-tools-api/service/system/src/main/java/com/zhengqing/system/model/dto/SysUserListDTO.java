package com.zhengqing.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统管理-用户基础信息表查询参数
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/15 10:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("系统管理-用户基础信息表查询参数")
public class SysUserListDTO {

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "名称")
    private String nickname;

}
