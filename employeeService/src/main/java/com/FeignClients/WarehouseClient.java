package com.FeignClients;

import com.Model.WarehouseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.security.FeignClientConfig;

import java.util.List;

@FeignClient(name="WAREHOUSE-SERVICE",configuration=FeignClientConfig.class)
public interface WarehouseClient {
    @GetMapping("/api/warehouses/manager/get/{warehouseId}")
    public WarehouseDTO getWarehouseById(@PathVariable Long warehouseId);

    @PostMapping("/api/warehouses/{warehouseId}/addEmployee/{employeeId}")
    public WarehouseDTO addEmployee(@PathVariable Long warehouseId, @PathVariable Long employeeId);

    @PostMapping("/api/warehouses/{warehouseId}/removeEmployee/{employeeId}")
    public WarehouseDTO removeEmployee(@PathVariable Long warehouseId, @PathVariable Long employeeId);

    @GetMapping("/api/warehouses/admanage/get")
    public List<WarehouseDTO> getAll();


}
