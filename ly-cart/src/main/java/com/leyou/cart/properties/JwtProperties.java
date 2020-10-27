package com.leyou.cart.properties;

import com.leyou.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @ClassName JwtProperties
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 11:56
 * @Version
 **/
@ConfigurationProperties(prefix = "leyou.jwt")
public class JwtProperties{
    private static final Logger    logger = LoggerFactory.getLogger(JwtProperties.class);
    private              String    pubKeyPath;
    private              PublicKey publicKey;
    private              String    cookieName;
    @PostConstruct
    public void init(){
        try{
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        }catch(Exception e){
            logger.error("初始化公钥失败！", e);
            throw new RuntimeException();
        }
    }

    public String getPubKeyPath(){
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath){
        this.pubKeyPath = pubKeyPath;
    }

    public PublicKey getPublicKey(){
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }

    public String getCookieName(){
        return cookieName;
    }

    public void setCookieName(String cookieName){
        this.cookieName = cookieName;
    }
}