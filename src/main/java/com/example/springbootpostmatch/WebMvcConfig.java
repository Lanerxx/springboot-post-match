package com.example.springbootpostmatch;

import com.example.springbootpostmatch.intercepter.AdminInterceptor;
import com.example.springbootpostmatch.intercepter.EnterpriseInterceptor;
import com.example.springbootpostmatch.intercepter.LoginInterceptor;
import com.example.springbootpostmatch.intercepter.StudentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private EnterpriseInterceptor enterpriseInterceptor;
    @Autowired
    private StudentInterceptor studentInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login");
        registry.addInterceptor(enterpriseInterceptor)
                .addPathPatterns("/api/enterprise**");
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/api/student**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin**");

    }

}
