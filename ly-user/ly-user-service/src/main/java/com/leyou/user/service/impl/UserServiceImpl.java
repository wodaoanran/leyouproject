package com.leyou.user.service.impl;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX="user:code:phone:";

    static final Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    public  Boolean chackData(String data,Integer type){
        User record=new User();
        switch (type){
            case 1: record.setUsername(data);break;
            case 2: record.setPhone(data);break;
            default:return null;
        }
        //没有查询出来 true 查询出来false
       return  userMapper.selectCount(record)==0;
    }

    @Override
    public Boolean sendVerityCode(String phone) {
        //生成的验证码
        String code = NumberUtils.generateCode(6);
        try {

            //发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);

            //消息队列发送消息
            amqpTemplate.convertAndSend("lly.sms.exchange", "sms.verify.code", msg);
            //存到redis里面 失效时间5分钟

            redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            LOGGER.error("发送短信失败，phone:code",phone,code);
            return false;
        }
    }

    @Override
    public Boolean register(User user, String code) {
        String key=KEY_PREFIX+user.getPhone();
        //redis中取验证码
        String codeCache = redisTemplate.opsForValue().get(key);
        //检查验证码是否一致
        if(!code.equals(codeCache)){
            //不正确 返回
            return false;
        }
        user.setId(null);
        user.setCreated(new Date());

        //写入数据库
        boolean boo = userMapper.insertSelective(user)==1;

        //写入成功 ，删除redis中code
        if(boo){
            try {
                redisTemplate.delete(key);
            }catch (Exception e){
                LOGGER.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return boo;
    }

    @Override
    public User queryUser(String username, String password) {

        //查询用户
        User record=new User();
        record.setUsername(username);
        record.setPassword(password);
        User user = userMapper.selectOne(record);
        //检查用户名
        if(user==null){
            return null;
        }
        return user;
    }

}
