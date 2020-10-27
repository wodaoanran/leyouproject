package com.leyou.cart.service.impl;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CartServiceImpl
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 12:18
 * @Version
 **/
@Service
public class CartServiceImpl implements CartService{
    //设置前缀
    static final String              KEY_PREFIX = "ly:cart:uid";
    @Autowired
    private      StringRedisTemplate redisTemplate;
    @Autowired
    private      GoodsClient         goodsClient;

    @Override
    public void addCart(Cart cart){
        //获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        //设置redis的key
        String key = KEY_PREFIX + user.getId();
        //先根据key查询购物车是否已经有该用户的购物车对象
        BoundHashOperations<String, Object, Object> hasOps = redisTemplate.boundHashOps(key);
        //获取购物车中的商品的skuid
        Long skuId = cart.getSkuId();
        //获取商品数量
        Integer num = cart.getNum();
        //判断是否有该购物车信息，有将信息解析成Cart对象，否则添加购物车
        Boolean boo = hasOps.hasKey(skuId);
        if(boo){
            //获取skuId
            String json = hasOps.get(skuId.toString()).toString();
            //解析
            cart = JsonUtils.parse(json, Cart.class);
            //获得商品总数
            cart.setNum(cart.getNum() + num);
        }else{
            //获取用户的id添加到购物车中
            cart.setUserId(user.getId());
            //获取商品id
            Sku sku = goodsClient.querySkuById(skuId);
            //添加图片
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            //添加价格
            cart.setPrice(sku.getPrice());
            //添加title
            cart.setTitle(sku.getTitle());
            //获取规格
            cart.setOwnSpec(sku.getOwnSpec());
        }
        //将购物车放到redis里面，以key，value的形式存储，将对象转换成json格式
        hasOps.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    @Override
    public List<Cart> queryCartList(){
        //获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        //查询购物车
        String key = KEY_PREFIX + user.getId();
        //判断redis里面这个key是否存在
        //不存在返回null;
        if(! redisTemplate.hasKey(key)){
            return null;
        }
        //获取所有购物车
        List<Object> carts = redisTemplate.boundHashOps(key).values();
        //如果购物车为空返回null
        if(CollectionUtils.isEmpty(carts)){
            return null;
        }
        //返回购物车redis列表
        return carts.stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
        //List collect =list.stream().map(String::toUpperCase).collect(Collectors.toList());
    }
}