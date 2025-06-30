package com.inventory.Auth_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import Controller.AuthController;
import Model.InventoryUsers;
import Service.CustomUserDetailsService;
import ServiceImpl.UserServiceImpl;
import Util.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.inventory.Auth_Service.AuthServiceApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    private InventoryUsers sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new InventoryUsers();
        sampleUser.setUsername("jaswanth");
        sampleUser.setPassword("password123");
        sampleUser.setEmpRole("ROLE_ADMIN");
        sampleUser.setAuthId(101L);
    }

    @Test
    void testRegisterById_Success() throws Exception {
        Mockito.when(userService.registerById(Mockito.eq(101L), any(InventoryUsers.class)))
                .thenReturn(sampleUser);

        mockMvc.perform(post("/api-auth/register/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved Successfully"));
    }

    @Test
    void testRegisterById_Failure() throws Exception {
        Mockito.when(userService.registerById(Mockito.eq(101L), any(InventoryUsers.class)))
                .thenReturn(null);

        mockMvc.perform(post("/api-auth/register/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed"));
    }

  
}
