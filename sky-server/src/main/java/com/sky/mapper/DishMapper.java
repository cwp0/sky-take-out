package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    /**
     * @Description: 根据主键id查询菜品信息
     * @Param: id      {java.lang.Long}
     * @Return: com.sky.entity.Dish
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 16:44
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * @Description: 根据主键id删除菜品数据
     * @Param: id      {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 17:04
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * @Description: 根据多个主键ids删除菜品数据
     * @Param: ids      {java.util.List<java.lang.Long>}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 18:57
     */
    void deleteByIds(List<Long> ids);

    /**
     * @Description: 根据主键id动态修改菜品数据
     * @Param: dish      {com.sky.entity.Dish}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 19:31
     */
    @AutoFill(OperationType.UPDATE) // 修改数据时自动填充操作人、操作时间
    void update(Dish dish);

    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

}
