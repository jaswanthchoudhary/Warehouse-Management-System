package com.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseDTO {

    private Long warehouseId;
    private String warehouseName;
    private Double warehouseCapacity;
    private Double warehouseCurrentCapacity;
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
    

}
