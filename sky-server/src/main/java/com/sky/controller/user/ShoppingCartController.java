package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.user
 * @Class: ShoppingCartController
 * @Description: 购物车模块
 * @Author: cwp0
 * @CreatedTime: 2024/07/15 21:46
 * @Version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * @Description: 添加购物车
     * @Param: shoppingCartDTO      {com.sky.dto.ShoppingCartDTO}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/15 21:51
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) { // 传入的参数是Body
        log.info("添加购物车，商品信息为：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}

