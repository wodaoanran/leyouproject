package com.leyou.config.myconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName MvcConfig
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 19:45
 * @Version
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Bean
    public FilterRegistrationBean someFilterRegistration1(){
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //添加写好的过滤器
        registration.setFilter(new MyFilter());
        //过滤路径
        registration.addUrlPatterns("/*");
        return registration;
    }

}