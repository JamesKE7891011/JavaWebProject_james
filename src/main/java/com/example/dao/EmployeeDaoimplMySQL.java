package com.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.bean.Employee;

@Repository("employeedaomysql")
public class EmployeeDaoimplMySQL implements EmployeeDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<Employee> findEmployeeById(Integer employeeId) {
		try {
			String sql = "select employeeId,employeeName from employee where employeeId = ?";
			Employee employee = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), employeeId);
			return Optional.ofNullable(employee);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} 		
	}	
}
