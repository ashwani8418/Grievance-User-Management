package com.user.restapi.services;

import com.user.restapi.dto.UserDTO;
import com.user.restapi.exceptionsHandler.NotFoundException;
import com.user.restapi.models.User;
import com.user.restapi.repository.UserRepo;
import com.user.restapi.utilities.RoleType;
import com.user.restapi.utilities.UserStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User registerUser(UserDTO userDTO) throws NotFoundException {
        if (userRepo.existsByUsername(userDTO.getUsername()) || userRepo.existsByEmail(userDTO.getEmail())) {
            throw new NotFoundException("Username is already in use");
        }
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setCountry(userDTO.getCountry());
        user.setRoles(Collections.singleton(RoleType.USER));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        user.setLastLoginAt(new Timestamp(System.currentTimeMillis()));
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) throws NotFoundException {
        User user = userRepo.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }
    @Override
    public User updateUser(Long id, UserDTO userDTO) throws NotFoundException {
        User user = userRepo.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        if(userDTO.getFullName() != null){
            user.setFullName(userDTO.getFullName());
        }
        if(userDTO.getUsername() != null){
            user.setUsername(userDTO.getUsername());
        }
        if(userDTO.getEmail() != null){
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getPassword() != null){
            user.setPassword(userDTO.getPassword());
        }
        if(userDTO.getPhone() != null){
            user.setPhone(userDTO.getPhone());
        }
        if(userDTO.getCountry() != null){
            user.setCountry(userDTO.getCountry());
        }
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return userRepo.save(user);
    }

    @Override
    public User deleteUser(Long id) throws NotFoundException {
        User user = userRepo.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        userRepo.deleteById(id);
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }

}
