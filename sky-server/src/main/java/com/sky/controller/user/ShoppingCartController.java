package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * @Description: 查看购物车列表
     * @Param:       {}
     * @Return: com.sky.result.Result<java.util.List<com.sky.entity.ShoppingCart>>
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 12:11
     */
    @GetMapping("/list")
    @ApiOperation("查看购物车列表")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车列表");
        List<ShoppingCart> list = shoppingCartService.showShoppingCart();
        return Result.success(list);
    }
}

