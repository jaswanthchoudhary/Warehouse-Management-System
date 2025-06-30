package com.Feign;


import com.Model.EmployeeDTO;
import com.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE",configuration= FeignClientConfig.class)
public interface EmployeeClient {

    @GetMapping("/api/employees/warehouse/{WarehouseId}")
    public List<EmployeeDTO> getEmployeeByWarehouseId(@PathVariable Long WarehouseId);

}
