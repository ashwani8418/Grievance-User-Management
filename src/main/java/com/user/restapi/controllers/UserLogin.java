package com.user.restapi.controllers;

import com.user.restapi.dto.LoginDTO;
import com.user.restapi.dto.UserDTO;
import com.user.restapi.exceptionsHandler.NotFoundException;
import com.user.restapi.models.User;
import com.user.restapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserLogin {

    private final UserService userService;

    @Autowired
    public UserLogin(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody UserDTO userDTO) throws NotFoundException {
        User user = userService.getUserByEmail(userDTO.getEmail());
        if(user == null){
            throw new NotFoundException("User not found");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatches = bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword());
        if(!passwordMatches){
            throw new NotFoundException("Password is incorrect");
        }
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(user.getEmail());
        loginDTO.setToken("token");
        return ResponseEntity.ok(loginDTO);
    }
}
