package com.kmd.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kmd.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
