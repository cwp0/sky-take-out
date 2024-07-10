package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * @Description: 插入员工数据
     * @Param: employee      {com.sky.entity.Employee}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 14:48
     */
    @Insert("insert into employee(name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user)" +
    "values " +
    "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    /**
     * @Description: 分页查询，这里不使用注解的方式插入SQL语句，因为需要使用到动态SQL，所以写入EmployeeMapper.xml映射文件里面
     * @Param: employeePageQueryDTO      {com.sky.dto.EmployeePageQueryDTO}
     * @Return: com.github.pagehelper.Page<com.sky.entity.Employee>
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 16:23
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * @Description: 根据主键动态修改员工数据
     * @Param: employee      {com.sky.entity.Employee}
     * @Return: void
     * @Author: cwp0
     * @CreatedTime: 2024/7/10 17:40
     */
    void update(Employee employee);
}
