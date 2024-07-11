package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
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

    /**
     * @Description: 根据菜品id删除口味数据
     * @Param: dishId     {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 17:06
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * @Description: 根据菜品ids删除口味数据
     * @Param: dishIds      {java.util.List<java.lang.Long>}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 19:03
     */
    void deleteByDishIds(List<Long> dishIds);
}
