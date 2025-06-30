package Service;

import Model.InventoryUsers;
import Repository.AuthRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;  // Correct import
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    public CustomUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	InventoryUsers user = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    	List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getEmpRole() != null && !user.getEmpRole().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(user.getEmpRole()));
        }

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}