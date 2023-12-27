package com.example.dao;

import java.util.Optional;

import com.example.bean.Employee;

public interface EmployeeDao {

	Optional<Employee> findEmployeeById(Integer employeeId);
}
