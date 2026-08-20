package com.echolife.backend;

import com.echolife.backend.dto.AuthResponse;
import com.echolife.backend.dto.LoginRequest;
import com.echolife.backend.dto.RegisterRequest;
import com.echolife.backend.entity.User;
import com.echolife.backend.repository.UserRepository;
import com.echolife.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Sarah Connor");
        sampleUser.setEmail("sarah@example.com");
        sampleUser.setPassword(authService.hashPassword("plainPassword123"));
        sampleUser.setRole("USER");
        sampleUser.setActive(true);
    }

    @Test
    @DisplayName("Should successfully register a new user")
    void registerUser_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Sarah Connor");
        request.setEmail("sarah@example.com");
        request.setPassword("plainPassword123");
        request.setRole("USER");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("Sarah Connor", response.getName());
        assertEquals("sarah@example.com", response.getEmail());
        assertEquals("Registration successful.", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when registering with duplicate email")
    void registerUser_DuplicateEmail_ThrowsException() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Sarah Connor");
        request.setEmail("sarah@example.com");
        request.setPassword("plainPassword123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(sampleUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );

        assertEquals("An account with this email already exists.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should successfully authenticate user with valid credentials")
    void loginUser_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("sarah@example.com");
        request.setPassword("plainPassword123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(sampleUser));

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("sarah@example.com", response.getEmail());
        assertEquals("Login successful.", response.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when user email does not exist")
    void loginUser_UserNotFound_ThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("plainPassword123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid email or password.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when login password is wrong")
    void loginUser_WrongPassword_ThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("sarah@example.com");
        request.setPassword("wrongPassword");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(sampleUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid email or password.", exception.getMessage());
    }
}