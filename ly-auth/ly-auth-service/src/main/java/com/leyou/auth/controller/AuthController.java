package com.leyou.auth.controller;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.properties.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Resource
    private JwtProperties properties;

    @PostMapping("accredit")
    public ResponseEntity<Void> authentication(@RequestParam String username,
                                               @RequestParam String password,
                                               HttpServletRequest request,
                                               HttpServletResponse response){

        //登录校验
        String token = authService.authentication(username, password);

        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //否则有值 发给客户端，存到客户端
        CookieUtils.setCookie(request,response,properties.getCookieName(),token,properties.getCookieMaxAge(),true);
        return ResponseEntity.ok().build();
    }
    //验证用户信息
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("LY_TOKEN") String token){

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, properties.getPublicKey());
            return ResponseEntity.ok(userInfo);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
