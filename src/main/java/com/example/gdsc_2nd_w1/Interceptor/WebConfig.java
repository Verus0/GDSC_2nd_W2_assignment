package com.example.gdsc_2nd_w1.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HandlerInterceptor authInterceptor;

    @Autowired
    public WebConfig(HandlerInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/orders")
                .addPathPatterns("/orders/**") // 주문과 관련된 경로에는 인터셉터 적용
                .excludePathPatterns("/login-form", "/login", "/signup-form", "/signup", "/logout"); // 로그인, 로그아웃, 회원가입 경로는 제외함
    }
}