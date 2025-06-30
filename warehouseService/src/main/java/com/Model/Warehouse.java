package com.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Warehouse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long warehouseId;
	private String warehouseName;
	private String warehouseAddress	;
	private Double warehouseCapacity;
	private Double warehouseCurrentCapacity;
	@ElementCollection
	List<Long> employeeList;
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getWarehouseAddress() {
		return warehouseAddress;
	}
	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}
	public Double getWarehouseCapacity() {
		return warehouseCapacity;
	}
	public void setWarehouseCapacity(Double warehouseCapacity) {
		this.warehouseCapacity = warehouseCapacity;
	}
	public Double getWarehouseCurrentCapacity() {
		return warehouseCurrentCapacity;
	}
	public void setWarehouseCurrentCapacity(Double warehouseCurrentCapacity) {
		this.warehouseCurrentCapacity = warehouseCurrentCapacity;
	}
	public List<Long> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Long> employeeList) {
		this.employeeList = employeeList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String description;



}
