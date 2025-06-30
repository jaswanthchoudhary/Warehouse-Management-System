package com.inventory.warehouseService;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.Model.Warehouse;
import com.ServiceImpl.warehouseServiceImpl;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.inventory.warehouseService.WarehouseServiceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class WarehouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private warehouseServiceImpl warehouseService;

    @Autowired
    private ObjectMapper objectMapper;

    private Warehouse sampleWarehouse;

    @BeforeEach
    void setUp() {
        sampleWarehouse = new Warehouse();
        sampleWarehouse.setWarehouseId(1L);
        sampleWarehouse.setWarehouseName("Test Warehouse");
        sampleWarehouse.setWarehouseAddress("Hyderabad");
        sampleWarehouse.setWarehouseCapacity(1000.0);
        sampleWarehouse.setWarehouseCurrentCapacity(200.0);
        sampleWarehouse.setDescription("Test Desc");
        sampleWarehouse.setEmployeeList(Arrays.asList(1L, 2L));
    }

    @Test
    void testCreateWarehouse() throws Exception {
        Mockito.when(warehouseService.createWarehouse(any(Warehouse.class))).thenReturn(sampleWarehouse);

        mockMvc.perform(post("/api/warehouses/admin/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleWarehouse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.warehouseName").value("Test Warehouse"));
    }

    @Test
    void testGetAllWarehouses() throws Exception {
        List<Warehouse> warehouses = Arrays.asList(sampleWarehouse);
        Mockito.when(warehouseService.getAllWarehouses()).thenReturn(warehouses);

        mockMvc.perform(get("/api/warehouses/manager/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].warehouseName").value("Test Warehouse"));
    }

    @Test
    void testGetWarehouseById() throws Exception {
        Mockito.when(warehouseService.getWarehouseById(1L)).thenReturn(sampleWarehouse);

        mockMvc.perform(get("/api/warehouses/manager/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseName").value("Test Warehouse"));
    }

    @Test
    void testUpdateWarehouse() throws Exception {
        Mockito.when(warehouseService.updateWarehouse(anyLong(), any(Warehouse.class))).thenReturn(sampleWarehouse);

        mockMvc.perform(put("/api/warehouses/admin/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleWarehouse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseName").value("Test Warehouse"));
    }

    @Test
    void testDeleteWarehouse() throws Exception {
        Mockito.doNothing().when(warehouseService).deleteWarehouse(1L);

        mockMvc.perform(delete("/api/warehouses/admin/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddEmployee() throws Exception {
        Mockito.when(warehouseService.addEmployee(1L, 10L)).thenReturn(sampleWarehouse);

        mockMvc.perform(post("/api/warehouses/1/addEmployee/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseName").value("Test Warehouse"));
    }

    @Test
    void testRemoveEmployee() throws Exception {
        Mockito.when(warehouseService.removeEmployee(1L, 10L)).thenReturn(sampleWarehouse);

        mockMvc.perform(post("/api/warehouses/1/removeEmployee/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseName").value("Test Warehouse"));
    }

    @Test
    void testAddCapacity() throws Exception {
        Mockito.when(warehouseService.getWarehouseById(1L)).thenReturn(sampleWarehouse);
        Mockito.when(warehouseService.addCapacity(1L, 50.0)).thenReturn(sampleWarehouse);

        mockMvc.perform(post("/api/warehouses/capacity/1/50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseName").value("Test Warehouse"));
    }
}
