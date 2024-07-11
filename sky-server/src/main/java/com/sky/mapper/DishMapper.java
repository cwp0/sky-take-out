package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * @Description: 插入菜品数据
     * @Param: dish      {com.sky.entity.Dish}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 13:45
     */
    @AutoFill(OperationType.INSERT) // 插入数据时自动填充操作人、操作时间、创建人、创建时间
    void insert(Dish dish);

    /**
     * @Description: 菜品分页查询
     * @Param: dishPageQueryDTO      {com.sky.dto.DishPageQueryDTO}
     * @Return: com.github.pagehelper.Page<com.sky.vo.DishVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 16:03
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
