package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传递过来的明文代码进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * @Description: 新增员工
     * @Param: employeeDTO      {com.sky.dto.EmployeeDTO}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 14:40
     */
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        // 使用对象属性拷贝工具类，将dto对象的属性拷贝到实体对象中
        BeanUtils.copyProperties(employeeDTO, employee);
        // 设置账号状态
        employee.setStatus(StatusConstant.ENABLE);
        // 新增员工使用默认密码 123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        // 设置当前记录的创建时间和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 设置当前记录的创建人id和修改人id
        // TODO: 后期需要改为当前登录用户的id
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        // 调用mapper插入数据
        employeeMapper.insert(employee);
    }

    /**
     * @Description: 员工分页查询
     * @Param: employeePageQueryDTO      {EmployeePageQueryDTO}
     * @Return: com.sky.result.PageResult
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 16:16
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // 开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        // 封装为PageResult对象
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * @Description: 员工账号状态的启用和禁用
     * @Param: id      {java.lang.Long}
     * @Param: status      {java.lang.Integer}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 17:37
     */
    public void startOrStop(Long id, Integer status) {
        Employee employee =new Employee();
        employee.setId(id);
        employee.setStatus(status);

        employeeMapper.update(employee);
    }

    /**
     * @Description: 根据id查询员工
     * @Param: id      {java.lang.Long}
     * @Return: com.sky.entity.Employee
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 19:38
     */
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        // 隐藏密码
        employee.setPassword("****");
        return employee;
    }

    /**
     * @Description: 编辑员工信息
     * @Param: employeeDTO      {com.sky.dto.EmployeeDTO}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 19:48
     */
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // 对象的属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

    /**
     * @Description: 修改员工密码
     * @Param: passwordEditDTO      {com.sky.dto.PasswordEditDTO}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 20:12
     */
    public void editPassword(PasswordEditDTO passwordEditDTO) {
        // 对旧密码进行校验
        String oldPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes());
        // 通过ThreadLocal获取当前登录用户的id
        Employee employee = employeeMapper.getById(BaseContext.getCurrentId());
        if(!oldPassword.equals(employee.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 对新密码进行加密
        employee.setPassword(DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes()));
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

}