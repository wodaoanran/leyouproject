package com.leyou.user.service;


import com.leyou.user.pojo.User;

public interface UserService {
    public  Boolean chackData(String data,Integer type);

    public Boolean sendVerityCode(String phone);

    public Boolean register(User user,String code);

    public User queryUser(String username,String password);
}
