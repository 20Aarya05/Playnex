package com.pbl.leaguemanagement.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbl.leaguemanagement.model.User;
import com.pbl.leaguemanagement.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("index")
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("register")
    public String showRegistrationPage() {
        return "register";
    }
    
    @PostMapping("register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String username,
                               @RequestParam int age,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String number,
                               @RequestParam String role) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setNumber(number);
        user.setRole(role);
        user.setRating(0);
        
        userService.saveUser(user);
        
        return "redirect:login";
    }

    @GetMapping("login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("email") String email,
                                                    @RequestParam("password") String password) {
        boolean isValidUser = userService.validateUser(email, password);

        Map<String, String> response = new HashMap<>();
        if (isValidUser) {
            User user = userService.findByEmail(email); // Fetch the user by email
            response.put("userId", String.valueOf(user.getUser_id()));  // Assuming `getId()` returns the user's ID
            response.put("name", user.getName());
            response.put("role", user.getRole()); // Include the role in the response
            
            // Log the role to confirm it is being retrieved correctly
            System.out.println("User role: " + user.getRole());

            return ResponseEntity.ok(response); // Returning JSON response with user details
        } else {
            response.put("error", "Invalid credentials"); // Include an error message in the response
            return ResponseEntity.ok(response); // Returning HTTP 200 OK with error message
        }
    }



    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, String>> getUserById(@PathVariable("id") String id) {
        User user = userService.findById(id);  // Assuming userService has a method to find user by ID
        if (user != null) {
            Map<String, String> userDetails = new HashMap<>();
            userDetails.put("userId", String.valueOf(user.getUser_id()));
            userDetails.put("name", user.getName());
            userDetails.put("age", String.valueOf(user.getAge()));
            userDetails.put("rating", String.valueOf(user.getRating()));
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/tournament/{id}")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    @GetMapping("dashboardp")
    public String showDashboardPagep() {
        return "dashboardp";
    }

    @GetMapping("dashboardc")
    public String showDashboardPagec() {
        return "dashboardc";
    }

    @GetMapping("dashboardo")
    public String showDashboardPageo() {
        return "dashboardo";
    }

    @GetMapping("forgot_password")
    public String showForgotPasswordPage() {
        return "forgot_password";
    }

    @GetMapping("tour_team_show")
    public String createTourATeamShowPage() {
        return "tour_team_show"; 
    }
}
