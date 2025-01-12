package com.pbl.leaguemanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
@Entity
@Table(name = "user") // Ensure this name doesn't conflict with reserved keywords in your DB
public class User {
    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "custom-id-generator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(
        name = "custom-id-generator",    
        strategy = "com.pbl.leaguemanagement.model.CustomIdGenerator"
    )
    private String user_id; // String to accommodate the custom ID format

    private String name;
    private String username;
    private int age;
    private String email;
    private String password;
    private String number;
    private String role; // userType (player, coach, owner)
    private float rating;
}
