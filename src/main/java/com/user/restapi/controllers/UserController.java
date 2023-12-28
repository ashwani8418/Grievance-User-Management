package com.user.restapi.controllers;

import com.user.restapi.dto.UserDTO;
import com.user.restapi.exceptionsHandler.NotFoundException;
import com.user.restapi.models.User;
import com.user.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // Perform validation and business logic checks if necessary
            if (userDTO == null || userDTO.getUsername() == null || userDTO.getPassword() == null) {
                return ResponseEntity.badRequest().body("Invalid user data");
            }

            String userPassword = userDTO.getPassword();
            // Instantiate a BCryptPasswordEncoder
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(userPassword);
            userDTO.setPassword(encodedPassword);



            // Call the UserService to register the user
            User registeredUser = userService.registerUser(userDTO);

            if (registeredUser != null) {
                return ResponseEntity.ok("User registered successfully");
            } else {
                return ResponseEntity.badRequest().body("User registration failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during user registration");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUser(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

//    Get user by id
//    path: /api/v1/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

//    Update user by id
//    path: /api/v1/users/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws NotFoundException {
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.badRequest().body("User not found");
        }
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("User updated successfully");
    }

//    Delete user by id
//    path: /api/v1/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws NotFoundException {
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.badRequest().body("User not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
