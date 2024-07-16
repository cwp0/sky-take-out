package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

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
}
