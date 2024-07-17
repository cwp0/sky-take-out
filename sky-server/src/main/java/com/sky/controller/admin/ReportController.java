package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

