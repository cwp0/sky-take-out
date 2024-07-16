package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service
 * @Interface: OrderService
 * @Description: 订单service
 * @Author: cwp0
 * @CreatedTime: 2024/07/16 13:37
 * @Version: 1.0
 */
public interface OrderService {

    /**
     * @Description: 用户下单
     * @Param: ordersSubmitDTO      {com.sky.dto.OrdersSubmitDTO}
     * @Return: com.sky.vo.OrderSubmitVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 13:39
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

}
