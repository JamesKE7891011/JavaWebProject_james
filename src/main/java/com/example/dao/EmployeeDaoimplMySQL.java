package com.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.bean.Employee;

@Repository("employeedaomysql")
public class EmployeeDaoimplMySQL implements EmployeeDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 查詢所有員工(多筆)
	@Override
	public List<Employee> findAllEmployees() {
		try {
				
			// SQL 查詢語句，選擇所有員工的相關資訊
			String sql = "select employeeId,employeeName from employee";
				
			// 使用 JdbcTemplate 的 query 方法執行 SQL 查詢，並使用 BeanPropertyRowMapper 將 ResultSet 中的資料映射到 Employee 物件
			return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
		} catch (EmptyResultDataAccessException e) {
				
			// 如果查詢結果為空，返回一個空的 ArrayList
			return new ArrayList<Employee>();
		} 	
	}	
	
	// 根據專案ID查找員工(單筆)
	@Override
	public Optional<Employee> findEmployeeById(Integer employeeId) {
		try {
			
			// SQL 查詢語句，根據 employeeId 選擇相應的員工資訊
			String sql = "select employeeId,employeeName from employee where employeeId = ?";
			
			// 使用 JdbcTemplate 的 queryForObject 方法執行 SQL 查詢，並使用 BeanPropertyRowMapper 將 ResultSet 中的資料映射到 Employee 物件
			Employee employee = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), employeeId);
			
			// 使用 Optional.ofNullable 將可能為 null 的 employee 物件轉換為 Optional<Employee>
			return Optional.ofNullable(employee);
		} catch (EmptyResultDataAccessException e) {
			
			// 如果查詢結果為空，返回一個空的 Optional
			return Optional.empty();
		} 		
	}
}
