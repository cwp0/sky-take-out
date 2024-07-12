package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

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
}
