package com.ServiceImpl;

import java.util.List;

import com.FeignClients.WarehouseClient;
import com.Model.WarehouseDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Model.Employees;
import com.Repository.employeeRepository;
import com.Service.employeeService;
import org.springframework.web.bind.annotation.GetMapping;


@Service
public class EmployeeServiceImpl implements employeeService {
	
	@Autowired
	employeeRepository empRepo;
	@Autowired
	WarehouseClient warehouseClient;

	//START OF CRUD OPERATIONS
	@Override
	@Transactional
	public Employees insertEmployee(Employees emp) {
//		Employees employees=empRepo.findById(emp.getEmpId()).orElseThrow(()->new RuntimeException("Emp not found"));
		if(empRepo.findByAuthId(emp.getAuthId())!=null) {
			throw new RuntimeException("AuthId already exists");
		}
		empRepo.save(emp);
		WarehouseDTO warehouse=warehouseClient.getWarehouseById(emp.getWarehouseId());
		warehouseClient.addEmployee(warehouse.getWarehouseId(),emp.getEmpId());
		return emp;
	}
	
	@Override
	public Employees getEmployeeById(Long id) {
		return empRepo.findById(id).orElseThrow(()-> new RuntimeException("Employee Record not found"));
	}


	@Override
	public Employees updateEmployees(Long empId, Employees empUpdates) {
		Employees emp=getEmployeeById(empId);
		if(emp.getEmpName()!=null && empUpdates.getEmpName() != null && !emp.getEmpName().equals(empUpdates.getEmpName())) {
			emp.setEmpName(empUpdates.getEmpName());
		}
		if(emp.getEmpGender()==null && empUpdates.getEmpGender()!=null) {
			emp.setEmpGender(empUpdates.getEmpGender());
		}
		if(emp.getEmpPhone()!=null && empUpdates.getEmpPhone()!=null && !emp.getEmpPhone().equals(empUpdates.getEmpPhone())) {
			emp.setEmpPhone(empUpdates.getEmpPhone());
		}
		if(emp.getEmpAddress()!=null && empUpdates.getEmpAddress()!=null) {
			emp.setEmpAddress(empUpdates.getEmpAddress());
		}
		if(emp.getAuthId()==null){
			emp.setAuthId(empUpdates.getAuthId());
		}
//		if(emp.getEmpCity()!=null) {
//			emp.setEmpCity()empUpdates.getEmpCity());
//		}
		if(emp.getEmpRole()!=null && empUpdates.getEmpRole()!=null && !emp.getEmpRole().equals(empUpdates.getEmpRole())) {
			emp.setEmpRole(empUpdates.getEmpRole());
		}
		if(emp.getEmpRole()==null && empUpdates.getEmpRole()!=null){
			emp.setEmpRole(empUpdates.getEmpRole());
		}
		if(empUpdates.getWarehouseId()!=null && empUpdates.getWarehouseId()!=emp.getWarehouseId()) {
			if(warehouseClient.getWarehouseById(empUpdates.getWarehouseId())!=null){
				warehouseClient.removeEmployee(emp.getWarehouseId(),emp.getEmpId());
				emp.setWarehouseId(empUpdates.getWarehouseId());
				warehouseClient.addEmployee(empUpdates.getWarehouseId(), emp.getEmpId());
			}
		}
		return empRepo.save(emp);
	}

	@Override
	public void DeleteEmployee(Long empId) {
		Employees emp=empRepo.findById(empId).orElseThrow(()->new RuntimeException("Employee Not Found"));
		WarehouseDTO warehouse=warehouseClient.getWarehouseById(emp.getWarehouseId());
		warehouseClient.removeEmployee(warehouse.getWarehouseId(),emp.getEmpId());
		empRepo.deleteById(empId);
	}

	@Override
	public List<Employees> getAllEmployees() {
		// TODO Auto-generated method stub
		List<Employees> emp=empRepo.findAll();
		return emp;
	}


	//END OF EMPLOYEE CRUD OPERATIONS


	@Override
	public List<Employees> getEmployeesByWarehouse(Long warehouseId) {
		List<Employees> emp=empRepo.findByWarehouseId(warehouseId);
		return emp;
	}

	public WarehouseDTO getWarehouseById(Long warehouseId){ //Communicates with Warehouse-Service
		return warehouseClient.getWarehouseById(warehouseId);
	}

	public Employees getEmployeeByAuth_Id(Long Auth_id){ //Communicates with Auth-Service
		return empRepo.findByAuthId(Auth_id);
	}

	//(FOR UI) to add employee to a warehouse from a list of warehouses
	public List<WarehouseDTO> getAllWarehouses(){
		return warehouseClient.getAll();
	}

}
