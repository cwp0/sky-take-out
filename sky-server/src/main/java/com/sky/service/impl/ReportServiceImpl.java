package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private WorkspaceService workspaceService;

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

    /**
     * @Description: 统计指定时间范围内的订单数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.OrderReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 17:06
     */
    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        OrderReportVO orderReportVO = new OrderReportVO();
        // 获取从begin到end的日期列表，存入dateList
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            // 计算指定日期的后一天
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        String join = StringUtils.join(dateList, ",");
        orderReportVO.setDateList(join);

        // 每天的订单总数 select count(id) from orders where create_time > beginTime and create_time < endTime
        List<Integer> totalOrderList = new ArrayList<>();
        // 每天的有效订单数 select count(id) from orders where create_time > beginTime and create_time < endTime and status = 5
        List<Integer> validOrderList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN); // data这天开始的时刻
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);  // data这天结束的时刻

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            Integer todayTotalOrder = orderMapper.countByMap(map);
            totalOrderList.add(todayTotalOrder);

            map.put("status", Orders.COMPLETED);
            Integer todayValidOrder = orderMapper.countByMap(map);
            validOrderList.add(todayValidOrder);
        }
        orderReportVO.setOrderCountList(StringUtils.join(totalOrderList, ","));
        orderReportVO.setValidOrderCountList(StringUtils.join(validOrderList, ","));
        Integer total = totalOrderList.stream().reduce(Integer::sum).get();
        Integer valid = validOrderList.stream().reduce(Integer::sum).get();
        orderReportVO.setTotalOrderCount(total);
        orderReportVO.setValidOrderCount(valid);
        double rate = total == 0.0 ? 0.0 : (double) valid / total;
        orderReportVO.setOrderCompletionRate(rate);

        return orderReportVO;
    }

    /**
     * @Description: 统计指定时间范围内的销售额前10的菜品数据
     * @Param: begin      {java.time.LocalDate}
     * @Param: end      {java.time.LocalDate}
     * @Return: com.sky.vo.SalesTop10ReportVO
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 18:41
     */
    @Override
    @Transactional // 需要查询订单表和订单详情表，需要保证数据的一致性，开启事务
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        SalesTop10ReportVO salesTop10ReportVO = new SalesTop10ReportVO();
        // select name,sum(od.number) num from order_detail od, orders o where od.order_id = o.id and o.status = 5 and o.order_time > ? and o.order_time < ? group by name order by num desc limit 10

        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> salesTop10 = orderMapper.getSalesTop10(beginTime, endTime);

        List<String> names = salesTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(names, ",");
        salesTop10ReportVO.setNameList(nameList);

        List<Integer> numbers = salesTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(numbers, ",");
        salesTop10ReportVO.setNumberList(numberList);

        return salesTop10ReportVO;
    }

    /**
     * @Description: 导出营业数据
     * @Param: response      {javax.servlet.http.HttpServletResponse}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/17 20:50
     */
    @Override
    public void exportBusinessData(HttpServletResponse response) {
        // 1. 查询数据库获取营业数据 -- 查询最近30天的数据
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);
        // 查询概览数据
        BusinessDataVO businessDataVO =  workspaceService.getBusinessData(LocalDateTime.of(dateBegin, LocalTime.MIN), LocalDateTime.of(dateEnd, LocalTime.MAX));
        // 2. 通过POI将数据写入Excel文件
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            // 基于模板创建一个Excel对象
            XSSFWorkbook excel = new XSSFWorkbook(resourceAsStream);
            // 获取Sheet页
            XSSFSheet sheet = excel.getSheet("sheet1");
            // 填充数据--时间
            sheet.getRow(1).getCell(1).setCellValue("时间：" + dateBegin + "至" + dateEnd);
            // 获得第4行
            XSSFRow row = sheet.getRow(3);
            row.getCell(2) .setCellValue(businessDataVO.getTurnover());
            row.getCell(4) .setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(6) .setCellValue(businessDataVO.getNewUsers());

            // 获得第5行
            row = sheet.getRow(4);
            row.getCell(2) .setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4) .setCellValue(businessDataVO.getUnitPrice());

            // 填充明细数据
            for (int i = 0; i < 30 ; i++) {
                LocalDate date = dateBegin.plusDays(i);
                // 查询某一天的营业数据
                BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                // 获取某一行
                row = sheet.getRow(7+i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }

            // 3. 通过输出流将Excel文件下载到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            // 4. 关闭资源
            out.close();
            excel.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

