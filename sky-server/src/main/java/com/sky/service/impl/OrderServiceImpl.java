package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private UserMapper userMapper;

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

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //商户订单号
                new BigDecimal(0.01), //支付金额，单位 元
                "苍穹外卖订单", //商品描述
                user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * @Description: 用户历史订单分页查询
     * @Param: pageNum      {int}
     * @Param: pageSize      {int}
     * @Param: status      {java.lang.Integer}
     * @Return: com.sky.result.PageResult
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 17:12
     */
    @Override
    public PageResult pageQuery4User(int pageNum, int pageSize, Integer status) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        ordersPageQueryDTO.setStatus(status);

        // 分页查询
        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);
        List<OrderVO> list = new ArrayList<>();

        // 查询出订单明细，并封装入OrderVO进行响应
        if (page != null && page.getTotalElements() > 0) {
            for (Orders orders : page) {
                Long orderId = orders.getId(); // 订单id

                // 查询订单明细
                List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

            }
        }


        return null;
    }

    /**
     * @Description: 查询订单详情
     * @Param: id      {java.lang.Long}
     * @Return: com.sky.vo.OrderVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 18:20
     */
    @Override
    public OrderVO details(Long id) {
        // 根据id查询订单
        Orders orders = orderMapper.getById(id);

        // 查询该订单对应的菜品/套餐明细
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());
        // 将该订单及其详情封装到OrderVO并返回
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    /**
     * @Description: 用户取消订单
     * @Param: id      {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 19:21
     */
    @Override
    public void userCancelById(Long id) throws Exception {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(id);

        // 校验订单是否存在
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
        if (ordersDB.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDB.getId());

        // 订单处于待接单状态下取消，需要进行退款
        if (ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            // 调用微信退款接口
            weChatPayUtil.refund(
                    ordersDB.getNumber(),  // 商户订单号
                    ordersDB.getNumber(),  // 商户退款单号
                    new BigDecimal(0.01),  // 退款金额，单位 元
                    new BigDecimal(0.01)); // 原订单金额

            // 支付状态修改为退款
            orders.setPayStatus(Orders.REFUND);
        }

        // 更新订单状态、取消原因、取消时间
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消订单");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * @Description: 再来一单
     * @Param: id   订单id   {java.lang.Long}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/16 19:58
     */
    @Override
    public void repetition(Long id) {
        // 查询当前用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

        // 将订单详情数据转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            // 将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id"); // id是原订单详情的id，不能复制
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        // 将购物车对象批量添加到数据库
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

}

