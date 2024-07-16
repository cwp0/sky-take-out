package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.mapper
 * @Interface: ShoppingCartMapper
 * @Description: 购物车操作的Mapper接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/15 22:01
 * @Version: 1.0
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     * @Description: 动态查询购物车列表
     * @Param: shoppingCart      {com.sky.entity.ShoppingCart}
     * @Return: java.util.List<com.sky.entity.ShoppingCart>
     * @Author: cwp0
     * @CreatedTime: 2024/7/15 22:03
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * @Description: 根据id查询购物车
     * @Param: shoppingCart      {com.sky.entity.ShoppingCart}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/15 22:14
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * @Description: 插入购物车数据
     * @Param: shoppingCart      {com.sky.entity.ShoppingCart}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/15 22:26
     */
    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) values (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * @Description: 根据用户id删除购物车数据
     * @Param: userId      {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 12:23
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    /**
     * @Description: 根据id删除购物车数据
     * @Param: id      {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 12:38
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);
}
