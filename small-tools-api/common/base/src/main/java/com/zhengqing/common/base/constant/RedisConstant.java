package com.zhengqing.common.base.constant;

/**
 * <p>
 * 全局常用变量 - Redis缓存
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 14:47
 */
public interface RedisConstant {

    /**
     * 随机code码生成
     */
    String GENERATE_RANDOM_CODE_KEY = "small-tools:generate-random-code";
    /**
     * 随机code码生成尝试次数记录 -- 用于码用尽告警
     */
    String GENERATE_RANDOM_CODE_RETRY_NUM_KEY = "small-tools:generate-random-code:retry-num";
    int GENERATE_RANDOM_CODE_MAX_RETRY_NUM = 5;

}