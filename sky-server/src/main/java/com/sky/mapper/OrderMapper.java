package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Program: sky-take-out
 * @Package: com.sky.mapper
 * @Interface: OrderMapper
 * @Description: 操作订单的mapper接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/16 13:41
 * @Version: 1.0
 */
@Mapper
public interface OrderMapper {

    /**
     * @Description: 插入订单数据
     * @Param: orders      {com.sky.entity.Orders}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 14:19
     */
    void insert(Orders orders);
}
