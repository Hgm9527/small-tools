package com.zhengqing.common.core.config;

import com.zhengqing.common.core.config.interceptor.HandlerInterceptorForJwtCustomUser;
import com.zhengqing.common.core.config.interceptor.HandlerInterceptorForLogTraceId;
import com.zhengqing.common.core.config.interceptor.HandlerInterceptorForTenantId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 注册拦截器
 * </p>
 *
 * @author zhengqingya
 * @description ex: 租户ID
 * @date 2021/1/13 14:41
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(new HandlerInterceptorForTenantId()).addPathPatterns("/**");
        registry.addInterceptor(new HandlerInterceptorForJwtCustomUser()).addPathPatterns("/**");
        registry.addInterceptor(new HandlerInterceptorForLogTraceId()).addPathPatterns("/**");
    }

}
