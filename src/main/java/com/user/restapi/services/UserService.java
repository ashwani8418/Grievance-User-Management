package com.user.restapi.services;

import com.user.restapi.dto.UserDTO;
import com.user.restapi.exceptionsHandler.NotFoundException;
import com.user.restapi.models.User;

import java.util.List;

public interface UserService {
    User registerUser(UserDTO userDTO) throws NotFoundException;
    List<User> getAllUsers();

    User getUserByEmail(String email) throws NotFoundException;
    User getUserById(Long id) throws NotFoundException;

    User updateUser(Long id, UserDTO userDTO) throws NotFoundException;

    User deleteUser(Long id) throws NotFoundException;;
}
