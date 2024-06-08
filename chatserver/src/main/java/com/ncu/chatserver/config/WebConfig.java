package com.ncu.chatserver.config;

import com.ncu.chatserver.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/captcha")
                .excludePathPatterns("/api/type/upload")
                .excludePathPatterns("/api/admin/login")
                .excludePathPatterns("/api/admin/register")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register");
    }
}