package com.inventory.productService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Feign.WarehouseClient;
import com.Model.Product;
import com.Model.WarehouseDTO;
import com.Service.ProductCrudService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = com.inventory.productService.ProductServiceApplication.class)
@AutoConfigureMockMvc(addFilters = false) 
public class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCrudService productCrudService;

    @MockBean
    private WarehouseClient warehouseClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId(1L);
        sampleProduct.setProductName("Test Product");
        sampleProduct.setProductType("Electronics");
        sampleProduct.setProductWeight(1.2);
        sampleProduct.setVolume(0.5);
        sampleProduct.setProductQuantity(10);
        sampleProduct.setProductLocation("A1");
        sampleProduct.setWarehouseId(1L);
        sampleProduct.setStatus("Available");
        sampleProduct.setProductManufacture(new Date());
        sampleProduct.setProductExpiry(new Date());
        sampleProduct.setCapacityOccupied(sampleProduct.getVolume() * sampleProduct.getProductQuantity());
    }

    @Test
    void testInsertProduct() throws Exception {
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        warehouseDTO.setWarehouseId(1L);
        warehouseDTO.setWarehouseCapacity(500.0);
        warehouseDTO.setWarehouseCurrentCapacity(100.0);

        Mockito.when(warehouseClient.getWarehouseById(anyLong())).thenReturn(warehouseDTO);
        Mockito.when(productCrudService.addProduct(any(Product.class))).thenReturn(true);

        mockMvc.perform(post("/api/products/admin/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProduct)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> productList = Arrays.asList(sampleProduct);
        Mockito.when(productCrudService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/products/workmanage/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @Test
    void testGetProductById() throws Exception {
        Mockito.when(productCrudService.getProductById(1L)).thenReturn(sampleProduct);

        mockMvc.perform(get("/api/products/workmanage/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Mockito.when(productCrudService.updateProduct(eq(1L), any(Product.class))).thenReturn(sampleProduct);

        mockMvc.perform(put("/api/products/admin/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    void testDeleteProductById() throws Exception {
        Mockito.doNothing().when(productCrudService).removeProduct(1L);

        mockMvc.perform(delete("/api/products/admin/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDispatchProduct() throws Exception {
        Mockito.doNothing().when(productCrudService).dispatchProduct(1L, any(Product.class));

        mockMvc.perform(post("/api/products/workmanage/dispatch/1/5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dispatched"));
    }

    @Test
    void testGetProductsByWarehouse() throws Exception {
        List<Product> products = Arrays.asList(sampleProduct);
        Mockito.when(productCrudService.getProductsByWarehouseId(1L)).thenReturn(products);

        mockMvc.perform(get("/api/products/warehouse/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1));
    }

    @Test
    void testGetCapacityOccupied() throws Exception {
        Mockito.when(productCrudService.getWarehouseCapacityOccupied(1L)).thenReturn(7.5);

        mockMvc.perform(get("/api/products/capacity/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("7.5"));
    }
}
