package com.Repository;

import com.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {

    List<Product> findByWarehouseId(Long warehouseId);

    @Modifying
    @Query("Update Product p set p.productQuantity=?1")
    int updateProductQuantity(int updatedQuantity);
}
