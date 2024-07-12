package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service
 * @Interface: UserService
 * @Description: 用户相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/12 16:45
 * @Version: 1.0
 */
public interface UserService {

    /**
     * @Description: 微信登录
     * @Param: userLoginDTO      {com.sky.dto.UserLoginDTO}
     * @Return: com.sky.entity.User
     * @Author: cwp0
     * @CreatedTime: 2024/7/12 16:47
     */
    User wxLogin(UserLoginDTO userLoginDTO);

}
