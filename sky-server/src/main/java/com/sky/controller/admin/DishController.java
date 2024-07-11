package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.admin
 * @Class: DishController
 * @Description: 菜品管理
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 13:26
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * @Description: 新增菜品
     * @Param: dishDTO      {com.sky.dto.DishDTO}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 13:29
     */
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) { // @RequestBody注解用于接收前端传递的json数据
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * @Description: 菜品分页查询
     * @Param: dishPageQueryDTO      {com.sky.dto.DishPageQueryDTO}
     * @Return: com.sky.result.Result<com.sky.result.PageResult>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 15:57
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Description: 根据ids批量删除菜品
     * @Param: ids      {java.util.List<java.lang.Long>}
     * @Return: com.sky.result.Result
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 16:29
     */
    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")
    public Result delete(@RequestParam List<Long> ids) { // 加了 @RequestParam 注解后，MVC框架会动态解析字符串，并把id提取出来，封装到ids中
        log.info("批量删除菜品：{}", ids);
        dishService.deleteBanch(ids);
        return Result.success();
    }

}

