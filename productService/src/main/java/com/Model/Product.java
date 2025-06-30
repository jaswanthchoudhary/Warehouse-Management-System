package com.Model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    String productName;

    String productType;
    Double productWeight;
    Double volume;
    int productQuantity;

    Double capacityOccupied;


    String productLocation;

    Long warehouseId;

    String status;
    Date productManufacture;
    Date productExpiry;

    public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(Double productWeight) {
		this.productWeight = productWeight;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Double getCapacityOccupied() {
		return capacityOccupied;
	}

	public void setCapacityOccupied(Double capacityOccupied) {
		this.capacityOccupied = capacityOccupied;
	}

	public String getProductLocation() {
		return productLocation;
	}

	public void setProductLocation(String productLocation) {
		this.productLocation = productLocation;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getProductManufacture() {
		return productManufacture;
	}

	public void setProductManufacture(Date productManufacture) {
		this.productManufacture = productManufacture;
	}

	public Date getProductExpiry() {
		return productExpiry;
	}

	public void setProductExpiry(Date productExpiry) {
		this.productExpiry = productExpiry;
	}

	@PrePersist
    @PreUpdate
    public void calculateCapacityOccupied(){
        this.capacityOccupied=this.volume*this.productQuantity;
    }


}
