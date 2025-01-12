package com.pbl.leaguemanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbl.leaguemanagement.model.User;
import com.pbl.leaguemanagement.repository.UserRepository; // Make sure this import is correct


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Validate user credentials (email and password)
    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email); // Assuming this exists in UserRepository
        return user != null && user.getPassword().equals(password); // Basic password validation
    }

    // Save a new user to the database
    public void saveUser(User user) {
        userRepository.save(user); // Save the user using JPA repository
    }

    // Find a user by their email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email); // Assuming this exists in UserRepository
    }

    // Find a user by their username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username); // Implement this in the repository if not already there
    }

    // Find a user by their ID
    public User findById(String id) {
        return userRepository.findById(id).orElse(null); // Fetch by ID, return null if not found
    }

    // Find user by email (redundant method - choose between this or findByEmail)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email); // You can remove this method if it's redundant
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        throw new UnsupportedOperationException
        ("Unimplemented method 'getUserByPhoneNumber'");
    }

}