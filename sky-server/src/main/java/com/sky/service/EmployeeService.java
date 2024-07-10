package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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

}
