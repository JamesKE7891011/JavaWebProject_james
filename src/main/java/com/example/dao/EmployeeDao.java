package com.example.dao;

import java.util.List;
import java.util.Optional;

import com.example.bean.Employee;

public interface EmployeeDao {

	List<Employee> findAllEmployees();
	
	Optional<Employee> findEmployeeById(Integer employeeId);
}
