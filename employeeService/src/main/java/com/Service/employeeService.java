package com.Service;

import java.util.List;

import com.Model.Employees;
import com.Model.WarehouseDTO;

public interface employeeService {
	
	Employees insertEmployee(Employees emp);
	Employees updateEmployees(Long empId, Employees empUpdates);
	void DeleteEmployee(Long empId);
	List<Employees> getAllEmployees();
	Employees getEmployeeById(Long id);
	List<Employees> getEmployeesByWarehouse(Long warehouseId);
	public WarehouseDTO getWarehouseById(Long warehouseId);

	Employees getEmployeeByAuth_Id(Long Auth_id);
	
}
