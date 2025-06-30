package com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Model.Warehouse;


@Repository
public interface warehouseRepository extends JpaRepository<Warehouse, Long> {

}
