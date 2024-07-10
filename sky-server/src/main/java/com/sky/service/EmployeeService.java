package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @Description: 新增员工
     * @Param: employee      {com.sky.entity.Employee}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 14:29
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * @Description: 员工分页查询
     * @Param: employeePageQueryDTO      {com.sky.dto.EmployeePageQueryDTO}
     * @Return: com.sky.result.PageResult
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 16:15
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * @Description: 员工账号状态的启用和禁用
     * @Param: id      {java.lang.Long}
     * @Param: status      {java.lang.Integer}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 17:35
     */
    void startOrStop(Long id, Integer status);

}
