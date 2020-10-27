package com.leyou.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @ClassName LyConfigApplication
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 19:27
 * @Version
 **/
@SpringBootApplication
@EnableConfigServer
public class LyConfigApplication{
    public static void main(String[] args){
        SpringApplication.run(LyConfigApplication.class,args);
    }
}