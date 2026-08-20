package com.echolife.backend;

import com.echolife.backend.controller.AuthController;
import com.echolife.backend.dto.AuthResponse;
import com.echolife.backend.dto.LoginRequest;
import com.echolife.backend.dto.RegisterRequest;
import com.echolife.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("POST /api/auth/register - Should return 201 Created on valid registration")
    void register_Success() throws Exception {
        AuthResponse mockResponse = new AuthResponse(
                1L,
                "Sarah Connor",
                "sarah@example.com",
                "USER",
                "Registration successful."
        );

        when(authService.register(any(RegisterRequest.class))).thenReturn(mockResponse);

        String jsonPayload = """
                {
                    "name": "Sarah Connor",
                    "email": "sarah@example.com",
                    "password": "password123",
                    "role": "USER"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("sarah@example.com"))
                .andExpect(jsonPath("$.name").value("Sarah Connor"));
    }

    @Test
    @DisplayName("POST /api/auth/register - Should return 409 Conflict when email already exists")
    void register_DuplicateEmail_ReturnsConflict() throws Exception {
        when(authService.register(any(RegisterRequest.class)))
                .thenThrow(new IllegalArgumentException("An account with this email already exists."));

        String jsonPayload = """
                {
                    "name": "Sarah Connor",
                    "email": "sarah@example.com",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("POST /api/auth/login - Should return 200 OK on valid credentials")
    void login_Success() throws Exception {
        AuthResponse mockResponse = new AuthResponse(
                1L,
                "Sarah Connor",
                "sarah@example.com",
                "USER",
                "Login successful."
        );

        when(authService.login(any(LoginRequest.class))).thenReturn(mockResponse);

        String jsonPayload = """
                {
                    "email": "sarah@example.com",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("sarah@example.com"))
                .andExpect(jsonPath("$.message").value("Login successful."));
    }

    @Test
    @DisplayName("POST /api/auth/login - Should return 401 Unauthorized on bad credentials")
    void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new IllegalArgumentException("Invalid email or password."));

        String jsonPayload = """
                {
                    "email": "sarah@example.com",
                    "password": "wrongPassword"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }
}