package com.zhengqing.common.core.config;

import com.zhengqing.common.feign.context.RequestHeaderHandler;
import com.zhengqing.common.web.util.RequestContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * <p>
 * Feign转发处理rpc调用传参
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/12/31 19:35
 */
@Slf4j
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    @SneakyThrows
    public void apply(RequestTemplate requestTemplate) {
        log.debug("========================== ↓↓↓↓↓↓ 《FeignRequestInterceptor》 Start... ↓↓↓↓↓↓ ==========================");
        // 新增手动设置的请求头值 （主要解决多线程异步+feign调用时请求头丢失问题）
        Map<String, String> threadHeaderNameMap = RequestHeaderHandler.getHeaderMap();
        if (!CollectionUtils.isEmpty(threadHeaderNameMap)) {
            threadHeaderNameMap.forEach((headerName, headerValue) -> {
                log.debug("《FeignRequestInterceptor》 多线程 headerName:【{}】 headerValue:【{}】", headerName, headerValue);
                requestTemplate.header(headerName, headerValue);
            });
        }
        Map<String, String> headerMap = RequestContextUtil.getHeaderMap();
        headerMap.forEach((headerName, headerValue) -> {
            log.debug("《FeignRequestInterceptor》 headerName:【{}】 headerValue:【{}】", headerName, headerValue);
            requestTemplate.header(headerName, headerValue);
        });
        log.debug("========================== ↑↑↑↑↑↑ 《FeignRequestInterceptor》 End... ↑↑↑↑↑↑ ==========================");
    }


}
