package com.zhengqing.common.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhengqing.common.util.MyDateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 数据定时任务
 * </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2020/4/12 0:48
 */
@Slf4j
@Component
public class Timer {

    /**
     * 每1小时执行一次
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void reportCurrentTime() {
        log.debug("现在时间：【{}】", MyDateUtil.nowStr());
    }

}
