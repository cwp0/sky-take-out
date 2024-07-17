package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

/**
 * @Program: sky-take-out
 * @Package: com.sky.service
 * @Interface: ReportService
 * @Description: 统计相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/17 14:55
 * @Version: 1.0
 */
public interface ReportService {

    /**
     * @Description: 统计指定时间范围内的营业额数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.TurnoverReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 14:58
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * @Description: 统计指定时间范围内的用户数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.UserReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 16:35
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * @Description: 统计指定时间范围内的订单数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.OrderReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 17:05
     */
    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    /**
     * @Description: 统计指定时间范围内的销售额前10的菜品数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.SalesTop10ReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 18:41
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
