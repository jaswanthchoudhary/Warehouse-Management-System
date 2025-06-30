package com.Feign;

import com.Model.WarehouseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.security.FeignClientConfig;

@FeignClient(name="WAREHOUSE-SERVICE",configuration=FeignClientConfig.class)
public interface WarehouseClient {

    @GetMapping("api/warehouses/admanage/get/{warehouseId}")
    public WarehouseDTO getWarehouseById(@PathVariable Long warehouseId);


    @PostMapping("/api/warehouses/capacity/{id}/{capacity}")
    public WarehouseDTO addCapacity(@PathVariable Long id,@PathVariable Double capacity);


}
