package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.mapper
 * @Interface: OrderDetailMapper
 * @Description: 订单明细表操作mapper
 * @Author: cwp0
 * @CreatedTime: 2024/07/16 13:42
 * @Version: 1.0
 */
@Mapper
public interface OrderDetailMapper {

    /**
     * @Description: 批量插入订单明细数据
     * @Param: orderDetailList      {java.util.List<com.sky.entity.OrderDetail>}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 14:32
     */
    void insertBatch(List<OrderDetail> orderDetailList);
}
