package com.inventory.employeeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Model.Employees;
import com.Model.WarehouseDTO;
import com.ServiceImpl.EmployeeServiceImpl;
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

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.inventory.employeeService.EmployeeServiceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl empService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employees sampleEmployee;
    private WarehouseDTO sampleWarehouse;

    @BeforeEach
    void setUp() {
        sampleEmployee = new Employees();
        sampleEmployee.setEmpId(1L);
        sampleEmployee.setAuthId(101L);
        sampleEmployee.setEmpName("John Doe");
        sampleEmployee.setEmpPhone(9876543210L);
        sampleEmployee.setEmpAddress("Hyderabad");
        sampleEmployee.setEmpGender("Male");
        sampleEmployee.setEmpRole("Manager");
        sampleEmployee.setWarehouseId(1L);

        sampleWarehouse = new WarehouseDTO();
        sampleWarehouse.setWarehouseId(1L);
        sampleWarehouse.setWarehouseName("Warehouse 1");
        sampleWarehouse.setWarehouseAddress("Delhi");
        
    }

    @Test
    void testCreateEmployee() throws Exception {
        Mockito.when(empService.insertEmployee(any(Employees.class))).thenReturn(sampleEmployee);

        mockMvc.perform(post("/api/employees/admanage/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empName").value("John Doe"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employees> employeeList = Arrays.asList(sampleEmployee);
        Mockito.when(empService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/employees/admanage/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empName").value("John Doe"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Mockito.when(empService.getEmployeeById(1L)).thenReturn(sampleEmployee);

        mockMvc.perform(get("/api/employees/admanage/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empName").value("John Doe"));
    }

    @Test
    void testGetEmployeeByWarehouseId() throws Exception {
        List<Employees> employeeList = Arrays.asList(sampleEmployee);
        Mockito.when(empService.getEmployeesByWarehouse(1L)).thenReturn(employeeList);

        mockMvc.perform(get("/api/employees/warehouse/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empId").value(1));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Mockito.when(empService.updateEmployees(eq(1L), any(Employees.class))).thenReturn(sampleEmployee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empName").value("John Doe"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Mockito.doNothing().when(empService).DeleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1 deleted"));
    }

    @Test
    void testGetWarehouseById() throws Exception {
        Mockito.when(empService.getWarehouseById(1L)).thenReturn(sampleWarehouse);

        mockMvc.perform(get("/api/employees/warehouses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseName").value("Warehouse 1"));
    }

    @Test
    void testGetEmployeeByAuthId() throws Exception {
        Mockito.when(empService.getEmployeeByAuth_Id(101L)).thenReturn(sampleEmployee);

        mockMvc.perform(get("/api/employees/auth-employee/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empId").value(1));
    }
}
