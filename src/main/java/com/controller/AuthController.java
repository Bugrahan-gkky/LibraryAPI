package com.controller;

import com.entity.User;
import com.security.JwtTokenProvider;
import com.service.UserService;
import com.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    // Not: İleride buraya JwtUtil veya AuthenticationManager sınıfını da enjekte edeceğiz.

    // PC Uygulamasından Yeni Kayıt (Register) İstemi
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Kayıt işlemini daha önce yazdığımız UserService'e devrediyoruz
        return ResponseEntity.ok(userService.createUser(user));
    }

    // PC Uygulamasından Giriş (Login) İstemi
    // AuthController.java içindeki login metodu:
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.getUserByEmail(email);

        if (user.getPassword().equals(password)) {
            // Şifre doğruysa, Token Provider'ı çağır ve e-posta ile token üret
            String token = jwtTokenProvider.generateToken(email);
            return ResponseEntity.ok(token); // PC Uygulamasına JWT döner
        } else {
            throw new BusinessException("Hatalı şifre girdiniz!");
        }
    }
}
