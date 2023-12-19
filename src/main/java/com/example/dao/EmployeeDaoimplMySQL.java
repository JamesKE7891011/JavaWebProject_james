package com.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Optional<Employee> findEmployeeById(int id) {
		String sql = "select employee_id,employee_name from projectdb WHERE employee_id";
		RowMapper<Employee> mapper = new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				// 取出資料列的每一個欄位資訊
				Integer employeeId = rs.getInt("employeeId");
				String employeeName = rs.getString("employeeName");
				// 將上述欄位資訊注入到 employee 物件中
				Employee employee = new Employee();
				employee.setEmployeeId(employeeId);
				employee.setEmployeeName(employeeName);
				
				return employee;
			}
		};
		
		List<Employee> employees = jdbcTemplate.query(sql, new Object[] { id }, mapper);
		return employees.isEmpty() ? Optional.empty() : Optional.of(employees.get(0));
	}
	
}
