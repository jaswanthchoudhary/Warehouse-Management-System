package com.Controller;

import java.util.List;

import com.Model.EmployeeDTO;
import com.ServiceImpl.warehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestParam;

import com.Model.Warehouse;

@RestController
@RequestMapping("/api/warehouses")
public class warehouseController {
	
	@Autowired
	warehouseServiceImpl service;

	
	@PostMapping("/admin/insert")
	public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse){
	    System.out.println("Saving warehouse: " + warehouse);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createWarehouse(warehouse));
	}
	
	@GetMapping("/admanage/get")
	public List<Warehouse> getAll(){

		return service.getAllWarehouses();
	}

	@GetMapping("/manager/get")
	public List<Warehouse> getAllM(){
		return service.getAllWarehouses();
	}
	
	@GetMapping("/admanage/get/{warehouseId}")
	public Warehouse getWarehouseById(@PathVariable Long warehouseId) {
		Warehouse warehouse= service.getWarehouseById(warehouseId);
		warehouse.setWarehouseCurrentCapacity(service.getCapacityOccupied(warehouseId));
		return service.getWarehouseById(warehouseId);
	}

	@GetMapping("/manager/get/{warehouseId}")
	public Warehouse getMWarehouseById(@PathVariable Long warehouseId) {
		Warehouse warehouse=service.getWarehouseById(warehouseId);
		warehouse.setWarehouseCurrentCapacity(service.getCapacityOccupied(warehouseId));
		return service.getWarehouseById(warehouseId);
	}
	
	
	@PutMapping("/admin/update/{warehouseId}")
	public Warehouse update(@PathVariable Long warehouseId, @RequestBody Warehouse updates) {
		return service.updateWarehouse(warehouseId, updates);
	}
	
	@DeleteMapping("/admin/delete/{warehouseId}")
	public ResponseEntity<Void> delete(@PathVariable Long warehouseId) {
		service.deleteWarehouse(warehouseId);
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping("/{warehouseId}/addEmployee/{employeeId}")
	public Warehouse addEmployee(@PathVariable Long warehouseId, @PathVariable Long employeeId) {
		return service.addEmployee(warehouseId, employeeId);
	}

	@PostMapping("/{warehouseId}/removeEmployee/{employeeId}")
	public Warehouse removeEmployee(@PathVariable Long warehouseId, @PathVariable Long employeeId) {
		return service.removeEmployee(warehouseId, employeeId);
	}


	@GetMapping("/capacity/{warehouseId}")
	public double getCurrentCapacityOccupied(@PathVariable Long warehouseId){
		return service.getCapacityOccupied(warehouseId);
	}

	@PostMapping("/capacity/{id}/{capacity}")
	public Warehouse addCapacity(@PathVariable Long id,@PathVariable Double capacity){
		Warehouse warehouse=getWarehouseById(id);
		if(warehouse.getWarehouseId()>0) {
			return service.addCapacity(id, capacity);
		}
		return null;
	}

	@GetMapping("/admin/warehouse/{warehouseId}")
	public List<EmployeeDTO> getEmployeesByWarehouseId(@PathVariable Long warehouseId){
		List<EmployeeDTO> employees=service.getEmployeesByWarehouseId(warehouseId);
		return employees;
	}
}
