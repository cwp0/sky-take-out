package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Program: sky-take-out
 * @Package: com.sky.config
 * @Class: RedisConfiguration
 * @Description: redis配置类
 * @Author: cwp0
 * @CreatedTime: 2024/07/11 21:40
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class RedisConfiguration {

    /**
     * @Description: 创建redis模板对象
     * @Param: redisConnectionFactory      {org.springframework.data.redis.connection.RedisConnectionFactory}
     * @Return: org.springframework.data.redis.core.RedisTemplate
     * @Author: cwp0
     * @CreatedTime: 2024/7/11 21:44
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置redis中ke的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


}

