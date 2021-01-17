package com.zhengqing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 数据源枚举类
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/9/13 18:46
 */
@Getter
@AllArgsConstructor
public enum DataSourceEnum {

    MASTER("master", "主数据库"), DB_TEST("db-test", "测试数据库");

    private final String value;
    private final String desc;

}
