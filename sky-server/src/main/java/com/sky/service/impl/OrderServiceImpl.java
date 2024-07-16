package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service.impl
 * @Class: OrderServiceImpl
 * @Description: 订单service实现类
 * @Author: cwp0
 * @CreatedTime: 2024/07/16 13:38
 * @Version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * @Description: 用户下单
     * @Param: ordersSubmitDTO      {com.sky.dto.OrdersSubmitDTO}
     * @Return: com.sky.vo.OrderSubmitVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 13:40
     */
    @Override
    @Transactional // 涉及多张表，需要开启事务
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        //1. 处理各种业务异常 (地址簿是否为空，购物车是否为空)
        // 查询地址簿是否为空
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            // 地址簿为空，抛出异常
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        // 查询购物车是否为空
        ShoppingCart shoppingCart = new ShoppingCart();
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            // 购物车为空，抛出异常
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2. 向订单表插入1条数据
        Orders orders = new Orders();
        // 对象的属性拷贝
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now()); // 下单时间
        orders.setPayStatus(Orders.UN_PAID); // 未支付
        orders.setStatus(Orders.PENDING_PAYMENT); // 待支付
        orders.setNumber(String.valueOf(System.currentTimeMillis())); // 使用时间戳作为订单号
        orders.setPhone(addressBook.getPhone()); // 手机号
        orders.setConsignee(addressBook.getConsignee()); // 收货人
        orders.setUserId(userId); // 用户id
        // 调用mapper插入数据
        orderMapper.insert(orders);

        //3. 向订单明细表插入n条数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        // 使用购物车数据生成订单明细
        for (ShoppingCart cart : shoppingCartList) {
            // 每个购物车数据生成一个订单明细
            OrderDetail orderDetail = new OrderDetail();
            // 对象的属性拷贝
            BeanUtils.copyProperties(cart, orderDetail);
            // 设置当前订单明细关联的订单id
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        // 批量插入订单明细
        orderDetailMapper.insertBatch(orderDetailList);

        //4. 用户下单成功后清空购物车
        shoppingCartMapper.deleteByUserId(userId);

        //5. 封装VO返回
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orders.getId());
        orderSubmitVO.setOrderTime(orders.getOrderTime());
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setOrderAmount(orders.getAmount());

        return orderSubmitVO;
    }
}
