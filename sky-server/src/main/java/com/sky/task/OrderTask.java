package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.task
 * @Class: OrderTask
 * @Description: 定时任务类，定时处理订单状态
 * @Author: cwp0
 * @CreatedTime: 2024/07/17 13:05
 * @Version: 1.0
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * @Description: 处理超时订单
     * @Param:       {}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 13:21
     */
    @Scheduled(cron = "0 * * * * ? ")
    public void processTimeoutOrder() {
        log.info("处理超时订单: {}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        // select * from orders where status = ? and order_time < (当前时间 - 15分钟)
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            ordersList.forEach(order -> {
                // 修改订单状态为已取消
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            });
        }
    }

    /**
     * @Description: 处理管理端一直处于配送中的订单
     * @Param:       {}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 13:22
     */
    @Scheduled(cron = "0 0 1 * * ? ")  // 每天凌晨1点执行
    public void processDeliveryOrder() {
        log.info("处理管理端一直处于配送中的订单: {}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            ordersList.forEach(order -> {
                // 修改订单状态为已完成
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            });
        }
    }

}

