package com.service;

import com.dto.response.UserResponse;
import com.entity.User;
import java.util.List;

public interface UserService {
    UserResponse createUser(User user);
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    User getUserEntityByEmail(String email); // AuthController'da şifre kontrolü için
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
}