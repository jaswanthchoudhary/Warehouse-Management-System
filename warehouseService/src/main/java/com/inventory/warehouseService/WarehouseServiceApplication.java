package com.inventory.warehouseService;

import com.Feign.ProductClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.Model.Warehouse;

@SpringBootApplication
@EntityScan(basePackageClasses = {Warehouse.class})  //
@EnableJpaRepositories({"com/Repository", "com.inventory.warehouseService.Repository"})
@ComponentScan({"com/Controller", "com/Service", "com/ServiceImpl", "com/Aspect",
               "com.inventory.warehouseService","com.security"})
@EnableFeignClients(basePackageClasses = {ProductClient.class})
@EnableAspectJAutoProxy
public class WarehouseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseServiceApplication.class, args);
	}

}
