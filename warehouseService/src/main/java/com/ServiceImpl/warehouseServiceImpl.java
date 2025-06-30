package com.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Feign.EmployeeClient;
import com.Feign.ProductClient;
import com.Model.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Model.Warehouse;
import com.Repository.warehouseRepository;
import com.Service.warehouseService;


@Service
public class warehouseServiceImpl implements warehouseService {

	@Autowired
	warehouseRepository repo;
	@Autowired
	ProductClient productClient;

	@Autowired
	EmployeeClient employeeClient;

	@Override
	public Warehouse createWarehouse(Warehouse warehouse) {
		return repo.save(warehouse);
	}
	
	
	@Override
	public Warehouse getWarehouseById(Long id) {
		Warehouse warehouse=repo.findById(id).orElseThrow(()-> new RuntimeException("Warehouse Does not exist"));
		warehouse.setWarehouseCurrentCapacity(productClient.capacityOccupiedByWarehouse(id));

		return warehouse;
	}

	
	@Override
	public List<Warehouse> getAllWarehouses() {
		List<Warehouse> warehouses=repo.findAll();

		for(Warehouse warehouse: warehouses){
			List<EmployeeDTO> employees= employeeClient.getEmployeeByWarehouseId(warehouse.getWarehouseId());
			List<Long> newEmployeeIds= employees.stream()
					.map(EmployeeDTO::getEmpId)
					.collect(Collectors.toCollection(ArrayList::new));

			warehouse.setWarehouseCurrentCapacity(getCapacityOccupied(warehouse.getWarehouseId()));

//			warehouse.setEmployeeList(employeeIds);
			if(!newEmployeeIds.equals(warehouse.getEmployeeList())){
				warehouse.setEmployeeList(newEmployeeIds);
				repo.save(warehouse);
			}
		}
		return warehouses;
	}
	
	@Override
	public Warehouse updateWarehouse(Long warehouseId, Warehouse warehouseUpdates) {
		Warehouse warehouse= getWarehouseById(warehouseId);
		if(warehouseUpdates.getWarehouseName()!=null) {
			warehouse.setWarehouseName(warehouseUpdates.getWarehouseName());
		}
		if(warehouseUpdates.getWarehouseAddress()!=null) {
			warehouse.setWarehouseAddress(warehouseUpdates.getWarehouseAddress());
		}
		if(warehouseUpdates.getWarehouseCapacity()>0) {
			warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity());
		}
		return repo.save(warehouse);
	}

	@Override
	public void deleteWarehouse(Long warehouseId) {
		repo.deleteById(warehouseId);
	}

	@Override
	public Warehouse addEmployee(Long warehouseId, Long employeeId) {
		Warehouse warehouse=getWarehouseById(warehouseId);
		warehouse.getEmployeeList().add(employeeId);
		return repo.save(warehouse);
	}


	public Warehouse removeEmployee(Long warehouseId, Long employeeId){
		Warehouse warehouse=getWarehouseById(warehouseId);
		warehouse.getEmployeeList().remove(employeeId);
		return repo.save(warehouse);
	}


	public double getCapacityOccupied(Long warehouseId){
		return productClient.capacityOccupiedByWarehouse(warehouseId);
	}


	public Warehouse addCapacity(Long id,Double capacity){
		Warehouse warehouse=repo.findById(id).orElseThrow(()->new RuntimeException("Warehouse not found current Id: "+id));
		warehouse.setWarehouseCurrentCapacity(warehouse.getWarehouseCurrentCapacity()+capacity);
		return repo.save(warehouse);
	}


	public List<EmployeeDTO> getEmployeesByWarehouseId(Long warehouseId){
		List<EmployeeDTO> employees=employeeClient.getEmployeeByWarehouseId(warehouseId);

		return employees;
	}

}
