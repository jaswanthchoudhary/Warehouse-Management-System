package com.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.security.FeignClientConfig;

@FeignClient(name = "PRODUCT-SERVICE",configuration=FeignClientConfig.class)
public interface ProductClient {
    @GetMapping("/api/products/capacity/{warehouseId}")
    double capacityOccupiedByWarehouse(@PathVariable Long warehouseId);



}
