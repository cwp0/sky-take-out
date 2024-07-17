package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Program: sky-take-out
 * @Package: com.sky.mapper
 * @Interface: UserMapper
 * @Description: 用户相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/12 18:31
 * @Version: 1.0
 */
@Mapper
public interface UserMapper {
    /**
     * @Description: 根据openid查询用户
     * @Param: openid      {java.lang.String}
     * @Return: com.sky.entity.User
     * @Author: cwp0
     * @CreatedTime: 2024/7/12 18:32
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * @Description: 插入用户数据
     * @Param: user      {com.sky.entity.User}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/12 18:49
     */
    void insert(User user);


    /**
     * @Description: 根据id查询用户
     * @Param: userId      {java.lang.Long}
     * @Return: com.sky.entity.User
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 15:54
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * @Description: 根据map动态条件查询用户数量
     * @Param: map      {java.util.Map}
     * @Return: java.lang.Integer
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 16:47
     */
    Integer countByMap(Map map);

}
