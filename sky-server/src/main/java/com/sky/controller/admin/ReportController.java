package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @Program: sky-take-out
 * @Package: com.sky.controller.admin
 * @Class: ReportController
 * @Description: 统计营业额数据相关接口
 * @Author: cwp0
 * @CreatedTime: 2024/07/17 14:48
 * @Version: 1.0
 */
@RestController
@Api(tags = "统计营业额数据相关接口")
@Slf4j
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * @Description: 营业额统计
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.result.Result<com.sky.vo.TurnoverReportVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 16:34
     */
    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("营业额统计：begin{}, end{}", begin, end);
        TurnoverReportVO turnoverStatistics = reportService.getTurnoverStatistics(begin, end);
        return Result.success(turnoverStatistics);
    }

    /**
     * @Description: 营业额统计
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.result.Result<com.sky.vo.UserReportVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 16:34
     */
    @ApiOperation("用户统计")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("用户统计：begin{}, end{}", begin, end);
        UserReportVO userStatistics = reportService.getUserStatistics(begin, end);
        return Result.success(userStatistics);
    }

    /**
     * @Description: 订单统计
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.result.Result<com.sky.vo.OrderReportVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 18:38
     */
    @ApiOperation("订单统计")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("订单统计：begin{}, end{}", begin, end);
        OrderReportVO orderStatistics = reportService.getOrdersStatistics(begin, end);
        return Result.success(orderStatistics);
    }

    /**
     * @Description: top10销量的商品
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.result.Result<com.sky.vo.SalesTop10ReportVO>
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 18:39
     */
    @GetMapping("/top10")
    @ApiOperation("top10销量的商品")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("top10销量的商品：begin{}, end{}", begin, end);
        SalesTop10ReportVO top10 = reportService.getSalesTop10(begin, end);
        return Result.success(top10);
    }

    /**
     * @Description: 导出报表
     * @Param: response      {javax.servlet.http.HttpServletResponse}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 20:49
     */
    @GetMapping("/export")
    @ApiOperation("导出报表")
    public void export(HttpServletResponse response) {
        reportService.exportBusinessData(response);
    }

}

