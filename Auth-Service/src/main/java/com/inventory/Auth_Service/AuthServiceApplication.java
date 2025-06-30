package com.inventory.Auth_Service;

import FiegnClients.EmployeeClient;
import Model.InventoryUsers;
import Repository.AuthRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = EmployeeClient.class)
@EntityScan(basePackageClasses = {InventoryUsers.class})
@EnableJpaRepositories(basePackageClasses = {AuthRepository.class})
@ComponentScan({"Controller", "Service", "ServiceImpl",
		"com.inventory.Auth_Service","Util","Config","com.security"})
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
