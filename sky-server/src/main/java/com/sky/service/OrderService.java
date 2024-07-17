package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

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

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * @Description: 用户端订单分页查询
     * @Param: page      {int}
     * @Param: pageSize      {int}
     * @Param: status      {java.lang.Integer}
     * @Return: com.sky.result.PageResult
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 17:11
     */
    PageResult pageQuery4User(int page, int pageSize, Integer status);

    /**
     * @Description: 查询订单详情
     * @Param: id      {java.lang.Long}
     * @Return: com.sky.vo.OrderVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 18:19
     */
    OrderVO details(Long id);

    /**
     * @Description: 用户取消订单
     * @Param: id      {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 19:20
     */
    void userCancelById(Long id) throws Exception;

    /**
     * @Description: 再来一单
     * @Param: id      {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 19:47
     */
    void repetition(Long id);

    /**
     * @Description: 条件搜索订单
     * @Param: ordersPageQueryDTO      {com.sky.dto.OrdersPageQueryDTO}
     * @Return: com.sky.result.PageResult
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 21:59
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO statistics();

    /**
     * 接单
     *
     * @param ordersConfirmDTO
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 派送订单
     *
     * @param id
     */
    void delivery(Long id);

    /**
     * 完成订单
     *
     * @param id
     */
    void complete(Long id);

}
