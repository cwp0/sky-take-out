package com.sky.mapper;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.github.pagehelper.Page;

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

    /**
     * @Description: 分页条件查询，并按照下单时间排序
     * @Param: ordersPageQueryDTO      {com.sky.dto.OrdersPageQueryDTO}
     * @Return: org.springframework.data.domain.Page<com.sky.entity.Orders>
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 17:16
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * @Description: 根据id查询订单信息
     * @Param: id      {java.lang.Long}
     * @Return: com.sky.entity.Orders
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 19:17
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 根据状态统计订单数量
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

}
