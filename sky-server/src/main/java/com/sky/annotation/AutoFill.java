package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Program: sky-take-out
 * @Package: com.sky.annotation
 * @Annotation: AutoFill
 * @Description: 自定义注解，用于标识某个方法需要进行字段自动填充处理
 * @Author: cwp0
 * @CreatedTime: 2024/07/10 21:06
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    // 数据库操作类型: UPDATE INSERT
    OperationType value();
}
