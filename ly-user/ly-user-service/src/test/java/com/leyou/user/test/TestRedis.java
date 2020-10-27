package com.leyou.user.test;

import com.leyou.user.LyUserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyUserApplication.class)
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;
    // string list hash set zset
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//string


    @Test
    public void testString(){
        redisTemplate.opsForValue().set("name","张三");
    }
    @Test
    public void testList(){

        redisTemplate.opsForList().leftPush("list",23);
        redisTemplate.opsForList().leftPush("list",25);
        redisTemplate.opsForList().leftPush("list",28);
        redisTemplate.opsForList().rightPush("list",56);
    }

    @Test
    public void testHash(){
        redisTemplate.opsForHash().put("map","aaa","111");
        redisTemplate.opsForHash().put("map","bbb","222");
        redisTemplate.opsForHash().put("map","ccc","333");
    }

    @Test
    public void testSet(){
      redisTemplate.opsForSet().add("set",10,11,12,13,14);

    }
    @Test
    public void testgetSet(){
        //查询所有
        Set set = redisTemplate.opsForSet().members("set");
        System.out.println("set: "+set);
    }

    @Test
    public void testSetCode(){
        stringRedisTemplate.boundValueOps("code").set("456321",10000, TimeUnit.MILLISECONDS);
    }
}
