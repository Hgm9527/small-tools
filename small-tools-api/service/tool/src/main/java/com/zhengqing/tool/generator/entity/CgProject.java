package com.zhengqing.tool.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengqing.common.db.entity.IsDeletedYesBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 代码生成器 - 项目管理表
 * </p>
 *
 * @author zhengqingya
 * @date 2019-09-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("代码生成器 - 项目管理表")
@TableName("t_cg_project")
public class CgProject extends IsDeletedYesBaseEntity<CgProject> {

    @ApiModelProperty(value = "项目ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "所属用户ID")
    private Integer dataReUserId;

    @ApiModelProperty(value = "状态：是否启用  0:停用  1:启用")
    private Integer status;

}
