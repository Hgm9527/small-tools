package com.zhengqing.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 系统管理 - 用户信息-展示内容
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/15 10:48
 */
@Data
@ApiModel("系统管理 - 用户信息-展示内容")
public class SysUserDetailVO {

    @ApiModelProperty(value = "主键ID")
    private Long userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * {@link com.zhengqing.common.core.enums.UserSexEnum}
     */
    @ApiModelProperty(value = "性别")
    private Byte sex;

    @ApiModelProperty("性别值")
    private String sexName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "是否删除：true->删除，false->未删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("角色ids")
    private String roleIds;

    @ApiModelProperty("角色")
    private String roleNames;

}