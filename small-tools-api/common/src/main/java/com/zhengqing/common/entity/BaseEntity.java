package com.zhengqing.common.entity;

import java.util.Date;

import javax.validation.constraints.Past;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * BaseEntity
 * </p>
 *
 * @description:
 * @author: zhengqing
 * @date: 2019/8/18 0018 1:30
 */
@Getter
@Setter
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {

    @ApiModelProperty(value = "是否有效(1:有效 0:无效）")
    @TableField(value = "is_valid", fill = FieldFill.INSERT)
    private Integer isValid;

    @ApiModelProperty(value = "创建人id")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Past(message = "创建时间必须是过去时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人id")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    // @Future(message = "修改时间必须是将来时间")
    private Integer updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
