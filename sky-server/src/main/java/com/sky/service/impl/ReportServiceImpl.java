package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service.impl
 * @Class: ReportServiceImpl
 * @Description: 统计相关接口实现类
 * @Author: cwp0
 * @CreatedTime: 2024/07/17 14:56
 * @Version: 1.0
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 统计指定时间范围内的营业额数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.TurnoverReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 15:00
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();

        // 获取从begin到end的日期列表，存入dateList
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            // 计算指定日期的后一天
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        String join = StringUtils.join(dateList, ",");
        turnoverReportVO.setDateList(join);

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            // 查询date日期对应的营业额，营业额指的是那些状态为已完成的订单的总金额
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // select sum(amount) from orders where order_time > beginTime and order_time < endTime and status = 5
            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        turnoverReportVO.setTurnoverList(StringUtils.join(turnoverList, ","));

        return turnoverReportVO;
    }

    /**
     * @Description: 统计指定时间范围内的用户数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.UserReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 16:35
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        UserReportVO userReportVO = new UserReportVO();

        // 获取从begin到end的日期列表，存入dateList
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            // 计算指定日期的后一天
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        String join = StringUtils.join(dateList, ",");
        userReportVO.setDateList(join);

        // 查询每天新增用户数 select count(id) from user where create_time > beginTime and create_time < endTime
        List<Integer> newUserList = new ArrayList<>();
        // 查询截止到每天的总用户数 select count(id) from user where create_time < endTime
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN); // data这天开始的时刻
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);  // data这天结束的时刻

            Map map = new HashMap();
            map.put("end", endTime);
            // 查询截止到date日期的总用户数
            Integer totalUser = userMapper.countByMap(map);
            totalUserList.add(totalUser);
            map.put("begin", beginTime);
            // 查询date日期新增用户数
            Integer newUser = userMapper.countByMap(map);
            newUserList.add(newUser);
        }
        userReportVO.setNewUserList(StringUtils.join(newUserList, ","));
        userReportVO.setTotalUserList(StringUtils.join(totalUserList, ","));
        return userReportVO;
    }

}

