package com.Service;

import com.Feign.WarehouseClient;
import com.Model.Product;
import com.Model.WarehouseDTO;
import com.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCrudService {
    @Autowired
    ProductRepository repo;
    @Autowired
    WarehouseClient warehouseClient;

    //CRUD OPERATIONS START//
    public boolean addProduct(Product product){
        boolean isSaved=false;
        WarehouseDTO warehouseDTO=warehouseClient.getWarehouseById(product.getWarehouseId());
//        warehouseDTO.setWarehouseCurrentCapacity(warehouseClient.getCurrentCapacityOccupied(product.getWarehouseId()));
        product.setCapacityOccupied(product.getVolume()* product.getProductQuantity());
        if(warehouseDTO.getWarehouseId()>0){
            if(warehouseDTO.getWarehouseCurrentCapacity()+product.getCapacityOccupied()<warehouseDTO.getWarehouseCapacity()) {
                repo.save(product);
                warehouseClient.addCapacity(warehouseDTO.getWarehouseId(), product.getCapacityOccupied());
                isSaved=true;
            }
        }
        return isSaved;

    }

    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getProductById(Long id){
        Product product=repo.findById(id).orElseThrow(()->new RuntimeException("Does not exist"));
        product.calculateCapacityOccupied();
        return product;
    }

    public void removeProduct(Long id){
        repo.deleteById(id);
    }

    public Product updateProduct(Long id,Product productUpdates) {
        Product product = repo.findById(id).orElseThrow(() -> new RuntimeException("Does not exist"));
        WarehouseDTO warehouse=warehouseClient.getWarehouseById(product.getWarehouseId());
        product.setProductName(productUpdates.getProductName());
//        product.setProductType(productUpdates.getProductType());
        product.setProductWeight(productUpdates.getProductWeight());
        product.setProductQuantity(productUpdates.getProductQuantity());
        product.setVolume(productUpdates.getVolume());
        product.setStatus(productUpdates.getStatus());
        product.setProductLocation(productUpdates.getProductLocation());
        product.setProductManufacture(productUpdates.getProductManufacture());
        product.setProductExpiry(productUpdates.getProductExpiry());
        product.setWarehouseId(productUpdates.getWarehouseId());
        product.calculateCapacityOccupied();
        if(product.getCapacityOccupied()<=warehouse.getWarehouseCapacity()) {
            return repo.save(product);
        }
        else return null;
    }

    public List<Product> getProductsByWarehouseId(Long warehouseId){
        return repo.findByWarehouseId(warehouseId);
    }

    //CRUD OPERATIONS END//




//    @Transactional
//    public void dispatchProduct(Long productId, int Quantity){
//        Product product=repo.findById(productId).orElseThrow(()->new RuntimeException(productId+ "Does not exist"));
//        int updatedQuantity=product.getProductQuantity()-Quantity;
//        product.calculateCapacityOccupied();
//        repo.updateProductQuantity(updatedQuantity);
//        if(updatedQuantity==0){
//            repo.deleteById(productId);
//        }
//
//        WarehouseDTO warehouse= warehouseClient.getWarehouseById(product.getWarehouseId());
//        warehouseClient.addCapacity(warehouse.getWarehouseId(),(double)-(Quantity*product.getVolume()));
////        repo.findById(product.getProductId());
//    }
    @Transactional
    public void dispatchProduct(Long productId, Product product){
        Product prod=repo.findById(productId).orElseThrow(()-> new RuntimeException(productId+" Does not exist"));
        int updatedQuantity=prod.getProductQuantity()-product.getProductQuantity();
        prod.calculateCapacityOccupied();
        prod.setProductQuantity(updatedQuantity);
        repo.save(prod);
        if(updatedQuantity==0){
            repo.deleteById(productId);
        }
        WarehouseDTO warehouse=warehouseClient.getWarehouseById(prod.getWarehouseId());
        warehouseClient.addCapacity(warehouse.getWarehouseId(),(double)-(product.getProductQuantity()*prod.getVolume()));
    }





    public double getWarehouseCapacityOccupied(Long warehouseId){ //this gives total capacity by all the products that belong to the same warehouse
        List<Product> products=repo.findByWarehouseId(warehouseId);
        return products.stream()
                .mapToDouble(Product::getCapacityOccupied)
                .sum();
    }


    public WarehouseDTO getWarehouseById(Long warehouseId){
        return warehouseClient.getWarehouseById(warehouseId);
    }



}