package com.echolife.backend.service;

import com.echolife.backend.entity.User;
import com.echolife.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Save user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 2. Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 3. Get user by ID (returns null if not found)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 4. Update an existing user
    public User updateUser(Long id, User updatedUserDetails) {
        // Find if the user exists in the database
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            // Overwrite old values with new values
            existingUser.setName(updatedUserDetails.getName());
            existingUser.setEmail(updatedUserDetails.getEmail());
            existingUser.setRole(updatedUserDetails.getRole());
            existingUser.setActive(updatedUserDetails.isActive());

            // Save updated entity back into PostgreSQL
            return userRepository.save(existingUser);
        }

        return null; // Return null if user does not exist
    }

    // 5. Delete a user by ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}