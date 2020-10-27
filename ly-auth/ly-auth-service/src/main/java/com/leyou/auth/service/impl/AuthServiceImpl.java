package com.leyou.auth.service.impl;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.properties.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Resource
    private JwtProperties properties;




    @Override
    public String authentication(String username, String password) {
        try {
            //查询
            User user = userClient.queryUser(username, password);
            //判断user登录是否成功
            if (user == null) {
                return null;
            }
            //登录成功，生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()), properties.getPrivateKey(), properties.getExpire());
            return token;
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
