package com.inventory.employeeService;

import com.FeignClients.WarehouseClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.Model.Employees;
import com.Repository.employeeRepository;

@SpringBootApplication
@EntityScan(basePackageClasses = {Employees.class})
@EnableJpaRepositories(basePackageClasses = {employeeRepository.class})
@ComponentScan({"com/Controller", "com/Service", "com/ServiceImpl", "com/Aspect",
"com.inventory.employeeService","com.security"})
@EnableFeignClients(basePackageClasses = {WarehouseClient.class})
@EnableAspectJAutoProxy
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}
