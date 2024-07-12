package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.user
 * @Class: UserController
 * @Description: 用户相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/12 16:37
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user/user")
@Api(tags = "C端-用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @Description:
     * @Param: userLoginDTO      {com.sky.dto.UserLoginDTO}
     * @Return: com.sky.result.Result<com.sky.vo.UserLoginVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/12 16:41
     */
    @PostMapping("/login")
    @ApiOperation(value = "微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) { // 传输的是json数据，所以使用@RequestBody
        log.info("微信用户登录，code:{}", userLoginDTO.getCode());
        // 微信用户登陆
        User user = userService.wxLogin(userLoginDTO);
        // 为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
}

