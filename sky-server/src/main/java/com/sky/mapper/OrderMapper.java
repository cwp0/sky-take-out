package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber} and user_id = #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

}
