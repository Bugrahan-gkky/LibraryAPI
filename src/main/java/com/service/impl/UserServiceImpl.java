package com.service.impl;

import com.dto.response.UserResponse;
import com.entity.User;
import com.exception.BusinessException;
import com.exception.ResourceNotFoundException;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Bu e-posta adresi sistemde zaten kayıtlı!");
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser); // Şifreyi gizleyip döner
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı! ID: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = getUserEntityByEmail(email);
        return userMapper.toResponse(user);
    }

    @Override
    public User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı! Email: " + email));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Silinecek kullanıcı bulunamadı! ID: " + id);
        }
        userRepository.deleteById(id);
    }
}