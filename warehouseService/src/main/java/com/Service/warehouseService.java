package com.Service;

import java.util.List;

import com.Model.Warehouse;

public interface warehouseService {
	
	Warehouse createWarehouse(Warehouse warehouse);
	Warehouse updateWarehouse(Long warehouseId, Warehouse warehouseUpdates);
	void deleteWarehouse(Long warehouseId);
	List<Warehouse> getAllWarehouses();
	Warehouse getWarehouseById(Long id);
	Warehouse addEmployee(Long warehouseId, Long employeeId);
}
