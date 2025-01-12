package com.pbl.leaguemanagement.controller;

import com.pbl.leaguemanagement.model.User;
import com.pbl.leaguemanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class TeamController {

    @Autowired
    private UserService userService;

    // Endpoint to get user details by phone number
    @GetMapping("/details")
    public ResponseEntity<UserResponse> getUserDetailsByPhoneNumber(@RequestParam String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);

        if (user != null) {
            // Create a response object containing the user's name and age
            UserResponse userResponse = new UserResponse(user.getName(), user.getAge());
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DTO (Data Transfer Object) for user response
    public static class UserResponse {
        private String name;
        private int age;

        public UserResponse(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
