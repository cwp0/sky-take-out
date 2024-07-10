package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Program: sky-take-out
 * @Package: com.sky.aspect
 * @Class: AutoFillAspect
 * @Description: 自定义切面类，统一拦截加入AutoFill字段的方法，切面 = 切入点 + 通知
 * @Author: cwp0
 * @CreatedTime: 2024/07/10 21:18
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * @Description: 切入点，拦截mapper包下所有加入AutoFill注解的方法
     * @Param:       {}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 21:24
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /**
     * @Description: 前置通知，在通知中进行公共字段的赋值
     * @Param: joinPoint      {org.aspectj.lang.JoinPoint}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 21:27
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("自动填充字段开始执行...");

        // 获取到当前被拦截方法上的数据库操作类型
        MethodSignature signature =  (MethodSignature) joinPoint.getSignature();
        OperationType operationType = signature.getMethod().getAnnotation(AutoFill.class).value();

        // 获取到当前被拦截方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        // 准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long userId = BaseContext.getCurrentId();

        // 根据数据库操作类型进行赋值，通过反射机制
        if (operationType == OperationType.INSERT) {
            // 如果是INSERT，则需要记录创建时间、创建人id、修改时间、修改人id
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                // 通过反射机制为对象赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, userId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, userId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // 如果是UPDATE，则需要记录修改时间、修改人id
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                // 通过反射机制为对象赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, userId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


    }

}

