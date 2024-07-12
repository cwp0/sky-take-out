package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service.impl
 * @Class: UserServiceImpl
 * @Description:
 * @Author: cwp0
 * @CreatedTime: 2024/07/12 16:45
 * @Version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // 微信接口地址
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;


    /**
     * @Description: 微信登录
     * @Param: userLoginDTO      {com.sky.dto.UserLoginDTO}
     * @Return: com.sky.entity.User
     * @Author: cwp0
     * @CreatedTime: 2024/7/12 17:03
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        // 调用微信接口获得当前微信用户的openid
        String openid = getOpenid(userLoginDTO.getCode());
        // 判断openid是否为空，如果为空则表示登录失败，抛出异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        // 判断当前用户是否为外卖系统的新用户
        User user = userMapper.getByOpenid(openid);

        // 如果是新用户，自动完成注册
        if (user == null) {
            // 如果是新用户，自动完成注册
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        // 返回用户对象
        return user;
    }

    /**
     * @Description: 调用微信接口获得当前微信用户的openid
     * @Param: code      {java.lang.String}
     * @Return: java.lang.String
     * @Author: cwp0
     * @CreatedTime: 2024/7/12 18:56
     */
    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);

        JSONObject jsonObject = JSONObject.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
