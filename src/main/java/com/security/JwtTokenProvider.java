package com.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Token'ı şifrelerken kullanılacak gizli anahtar (Gerçek projede application.yml'den çekilir)
    private final String jwtSecret = "KutuphaneOtomasyonuIcinCokGizliBirAnahtarKelime123456789";

    // Token'ın geçerlilik süresi (Örn: 24 saat = 86400000 milisaniye)
    private final int jwtExpirationMs = 86400000;

    // Başarılı giriş sonrası Token üretir
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Gelen Token'ın içinden kullanıcının e-postasını okur
    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // Token'ın süresi dolmuş mu veya sahte mi diye kontrol eder
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}