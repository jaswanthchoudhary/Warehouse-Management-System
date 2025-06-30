package FiegnClients;

import Model.EmployeeDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.security.FeignClientConfig;



@FeignClient(name="EMPLOYEE-SERVICE",configuration=FeignClientConfig.class)
public interface EmployeeClient {

    @GetMapping("/api/employees/auth-employee/{authId}")
    public EmployeeDTO getEmployeeByAuth_Id(@PathVariable Long authId);

}
