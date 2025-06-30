package Controller;

import Model.InventoryUsers;
import Model.LoginResponse;
import Repository.AuthRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import Service.CustomUserDetailsService;
import ServiceImpl.UserServiceImpl;
import Util.JwtTokenUtil;

@RestController
@RequestMapping("/api-auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserServiceImpl userService;
    private final AuthRepository auth;
    public AuthController(AuthenticationManager authenticationManager,
                        CustomUserDetailsService userDetailsService,
                        JwtTokenUtil jwtTokenUtil,
                        UserServiceImpl userService,AuthRepository auth) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.auth=auth;
    }

    @PostMapping("/register/{auth_id}")
    public ResponseEntity<String> registerByAuthID(@PathVariable Long auth_id, @RequestBody InventoryUsers credential) {
        InventoryUsers savedUser = userService.registerById(auth_id, credential);
        return savedUser != null ? 
            ResponseEntity.ok("Saved Successfully") : 
            ResponseEntity.badRequest().body("Failed");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody InventoryUsers authRequest) throws Exception {
    	authenticate(authRequest.getUsername(), authRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Fetch authId from the database (from InventoryUsers)
        InventoryUsers user = auth.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long authId = user.getAuthId();

        // Return both token and authId
        return ResponseEntity.ok(new LoginResponse(token, authId));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
