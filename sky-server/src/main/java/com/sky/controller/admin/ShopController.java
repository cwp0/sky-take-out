package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.admin
 * @Class: ShopController
 * @Description: 店铺相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 22:23
 * @Version: 1.0
 */
@RestController("adminShopController") // 设置bean名称，防止与用户端的ShopController冲突
@RequestMapping("/admin/shop")
@Api("店铺相关接口")
@Slf4j
public class ShopController {

    public static final String SHOP_STATUS = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Description: 设置店铺的营业状态
     * @Param: status      {java.lang.Integer}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 22:28
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(SHOP_STATUS, status);
        return Result.success();
    }

    /**
     * @Description: 获取店铺的营业状态
     * @Param:       {}
     * @Return: com.sky.result.Result<java.lang.Integer>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 22:29
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("获取店铺的营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }

}

