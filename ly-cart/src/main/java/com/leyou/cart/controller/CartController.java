package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName CartController
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 12:21
 * @Version
 **/
@Controller
public class CartController{
    @Autowired
    private CartService service;

    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        service.addCart(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> queryCartList(){
        List<Cart> carts = service.queryCartList();
        if(carts == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }
}