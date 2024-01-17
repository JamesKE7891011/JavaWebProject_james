package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Employee;

public interface EmployeeDao {

	// 查詢所有員工(多筆)
	List<Employee> findAllEmployees();
	
	// 根據專案ID查找員工(單筆)
	Optional<Employee> findEmployeeById(Integer employeeId);
}
