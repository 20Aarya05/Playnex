/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.pbl.leaguemanagement.controller;

 import com.pbl.leaguemanagement.model.User;
 
 class userService {
     public boolean authenticate(String username, String password) {
         // Logic to authenticate the user
         User user = userRepository.findByUsername(username);
 
         return user != null && user.getPassword().equals(password);
     }
 }
 