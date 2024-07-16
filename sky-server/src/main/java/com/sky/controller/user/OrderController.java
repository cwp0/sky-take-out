package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.dto.OrdersSubmitDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.user
 * @Class: OrderController
 * @Description: 订单相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/16 13:32
 * @Version: 1.0
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "C端-订单相关接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @Description: 用户下单
     * @Param: ordersSubmitDTO      {com.sky.dto.OrdersSubmitDTO}
     * @Return: com.sky.result.Result<com.sky.vo.OrderSubmitVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 13:36
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) { // 数据格式是json，所以用@RequestBody
        log.info("用户下单，订单信息：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

}

