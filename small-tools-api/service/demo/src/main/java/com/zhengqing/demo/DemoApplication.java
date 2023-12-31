package com.zhengqing.demo;

import com.zhengqing.common.base.constant.AppConstant;
import com.zhengqing.common.base.constant.ServiceConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableRetry // 启用重试
@SpringBootApplication
@ComponentScan(basePackages = {ServiceConstant.SERVICE_BASE_PACKAGE})
@EnableDiscoveryClient // 开启服务注册发现功能
@EnableTransactionManagement
@EnableFeignClients(basePackages = {AppConstant.RPC_BASE_PACKAGE}) // 开启Feign并扫描Feign客户端
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
