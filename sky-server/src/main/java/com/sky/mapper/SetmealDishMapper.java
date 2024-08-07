package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.mapper
 * @Interface: SetmealDishMapper
 * @Description: 套餐菜品关联Mapper
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 16:51
 * @Version: 1.0
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * @Description: 根据菜品ids查询对应的套餐ids
     * @Param: dishIds      {java.util.List<java.lang.Long>}
     * @Return: java.util.List<java.lang.Long>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 16:52
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量保存套餐和菜品的关联关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id删除套餐和菜品的关联关系
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据套餐id查询套餐和菜品的关联关系
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
