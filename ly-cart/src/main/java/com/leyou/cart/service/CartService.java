package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

public interface CartService{
    //添加购物车
    public void addCart(Cart cart);
    //获取购物车
    public List<Cart> queryCartList();
}
