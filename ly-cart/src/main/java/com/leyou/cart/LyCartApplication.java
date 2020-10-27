package com.leyou.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName LyCartApplication
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 13:59
 * @Version
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LyCartApplication{
    public static void main(String[] args){
        SpringApplication.run(LyCartApplication.class,args);
    }
}