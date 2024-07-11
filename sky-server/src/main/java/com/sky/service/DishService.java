package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service
 * @Interface: DishService
 * @Description: 菜品服务接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 13:30
 * @Version: 1.0
 */
public interface DishService {
    /**
     * @Description: 新增菜品和对应的口味
     * @Param: dishDTO      {com.sky.dto.DishDTO}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 13:31
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * @Description: 菜品分页查询
     * @Param: dishPageQueryDTO      {com.sky.dto.DishPageQueryDTO}
     * @Return: com.sky.result.PageResult
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 15:59
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
