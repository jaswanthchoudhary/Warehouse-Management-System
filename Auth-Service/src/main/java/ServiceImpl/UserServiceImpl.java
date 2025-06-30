package ServiceImpl;

import Model.EmployeeDTO;
import Model.InventoryUsers;
import Repository.AuthRepository;
import Service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import FiegnClients.EmployeeClient;

@Service
public class UserServiceImpl implements AuthService {
    private final AuthRepository authRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeClient empClient;

    public UserServiceImpl(AuthRepository authRepo, 
                         PasswordEncoder passwordEncoder,
                         EmployeeClient empClient) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
        this.empClient = empClient;
    }

    public InventoryUsers registerById(Long auth_id, InventoryUsers credential) {
        EmployeeDTO employee = empClient.getEmployeeByAuth_Id(auth_id);
        if (employee.getAuthId() != null) {
            InventoryUsers newUser = new InventoryUsers();
            newUser.setUsername(credential.getUsername());
            newUser.setPassword(passwordEncoder.encode(credential.getPassword()));
            newUser.setAuthId(employee.getAuthId());
            newUser.setEmpRole(employee.getEmpRole());
            return authRepo.save(newUser);
        }
        return null;
    }
}