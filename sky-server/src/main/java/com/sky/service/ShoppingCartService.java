package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service
 * @Interface: ShoppingCartService
 * @Description: 购物车相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/15 21:53
 * @Version: 1.0
 */
public interface ShoppingCartService {
    /**
     * @Description: 添加购物车
     * @Param: shoppingCartDTO      {ShoppingCartDTO}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/15 21:54
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * @Description: 查看购物车列表
     * @Param:       {}
     * @Return: java.util.List<com.sky.entity.ShoppingCart>
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 12:12
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * @Description: 清空购物车
     * @Param:       {}
     * @Return: java.lang.Object
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 12:20
     */
    void cleanShoppingCart();

    /**
     * @Description: 删除购物车中的一个商品
     * @Param: shoppingCartDTO      {com.sky.dto.ShoppingCartDTO}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 12:32
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
