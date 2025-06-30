package com.inventory.productService;

import com.Feign.WarehouseClient;
import com.Model.Product;
import com.Repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com/Repository", "com/Controller", "com/Service","com/Aspect","com.inventory.productService","com.security"})
@EnableJpaRepositories(basePackageClasses = {ProductRepository.class})
@EntityScan(basePackageClasses = {Product.class})
@EnableFeignClients(basePackageClasses = {WarehouseClient.class})
@EnableAspectJAutoProxy
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
