package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MvcConfig
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 12:22
 * @Version
 **/
@Configuration
//开启属性配置
@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer{
    @Autowired
    JwtProperties jwtProperties;
    //注入拦截器
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor(jwtProperties);
    }
    //添加拦截器拦截路径为/**
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
    }
}