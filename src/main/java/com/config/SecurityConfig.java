package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // PC uygulamasından gelen isteklerde CSRF korumasına ihtiyacımız yok
                .csrf(AbstractHttpConfigurer::disable)
                // JWT kullandığımız için sunucuda oturum (session) tutmuyoruz, her şey stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // AuthController'daki giriş ve kayıt işlemlerine herkes erişebilir
                        .requestMatchers("/api/auth/**").permitAll()
                        // Geri kalan TÜM isteklere (Kitap ekleme, ödünç alma vb.) kimlik doğrulaması zorunludur
                        .anyRequest().authenticated()
                );

        // Not: İleride JwtAuthenticationFilter'ı da buraya ekleyeceğiz.
        return http.build();
    }
}