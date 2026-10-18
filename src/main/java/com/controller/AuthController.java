package com.controller;

import com.dto.request.LoginRequest;
import com.dto.response.TokenResponse;
import com.dto.response.UserResponse;
import com.entity.User;
import com.exception.BusinessException;
import com.security.JwtTokenProvider;
import com.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        // Şifre kontrolü için gerçek Entity'yi çekiyoruz
        User user = userService.getUserEntityByEmail(request.getEmail());

        if (user.getPassword().equals(request.getPassword())) {
            String token = jwtTokenProvider.generateToken(user.getEmail());

            // PC uygulamasına Token, Email ve Rol bilgisini paket halinde dönüyoruz
            TokenResponse response = TokenResponse.builder()
                    .token(token)
                    .email(user.getEmail())
                    .role(user.getRole() != null ? user.getRole().name() : "MEMBER")
                    .build();

            return ResponseEntity.ok(response);
        } else {
            throw new BusinessException("Hatalı şifre girdiniz!");
        }
    }
}