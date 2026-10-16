package com.controller;

import com.entity.User;
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
    // Not: İleride buraya JwtUtil veya AuthenticationManager sınıfını da enjekte edeceğiz.

    // PC Uygulamasından Yeni Kayıt (Register) İstemi
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Kayıt işlemini daha önce yazdığımız UserService'e devrediyoruz
        return ResponseEntity.ok(userService.createUser(user));
    }

    // PC Uygulamasından Giriş (Login) İstemi
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {

        User user = userService.getUserByEmail(email);

        // Şimdilik sadece basit bir metin kontrolü yapıyoruz.
        if (user.getPassword().equals(password)) {
            // Başarılı olursa PC uygulamasına bir giriş anahtarı dönüyoruz
            return ResponseEntity.ok("Giriş başarılı! (Security katmanı yazıldığında burası JWT Token dönecek)");
        } else {
            // Parola eşleşmezse kendi yazdığımız özel hatayı fırlatıyoruz
            throw new BusinessException("Hatalı şifre girdiniz!");
        }
    }
}
