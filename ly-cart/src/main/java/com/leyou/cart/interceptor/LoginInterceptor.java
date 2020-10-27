package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.properties.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 15:48
 * @Version
 **/
//token拦截器
public class LoginInterceptor extends HandlerInterceptorAdapter{
    //创建一个静态常量线程
    public static final ThreadLocal<UserInfo> t1 = new ThreadLocal();
    @Autowired
    private             JwtProperties         jwtProperties;

    public LoginInterceptor(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //查询token
        String token = CookieUtils.getCookieValue(request, "LY_TOKEN");
        if(StringUtils.isBlank(token)){
            //未登录，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        //登录查询user信息
        try{
            UserInfo user = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            t1.set(user);
        }catch(Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return true;
        }
            return  false;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        t1.remove();
    }
    public static UserInfo getLoginUser(){
        return t1.get();
    }
}