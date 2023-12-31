package com.zhengqing.demo.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 测试demo展示视图
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Data
@ApiModel("测试demo展示视图")
public class DemoListVO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("用户信息list")
    private List<UserObj> userList;

    @ApiModelProperty("用户信息")
    private UserObj userObj;

    @Data
    @ApiModel("测试demo -- mybatis对象映射")
    static class UserObj {

        @ApiModelProperty("用户名")
        private String username;

        @ApiModelProperty("密码")
        private String password;

    }

}
