package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.mapper
 * @Interface: DishFlavorMapper
 * @Description: 菜品口味mapper
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 14:17
 * @Version: 1.0
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * @Description: 批量插入口味数据
     * @Param: flavors      {java.util.List<com.sky.entity.DishFlavor>}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 14:19
     */
    void insertBatch(List<DishFlavor> flavors);
}
