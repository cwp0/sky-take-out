package com.sky.service;

import com.sky.vo.TurnoverReportVO;

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
    
}
