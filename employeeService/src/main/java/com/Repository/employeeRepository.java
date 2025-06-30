package com.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Model.Employees;

@Repository
public interface employeeRepository extends JpaRepository<Employees, Long> {

	
	List<Employees> findByWarehouseId(Long warehouseId);
	Employees findByAuthId(Long AuthId);
	
}
