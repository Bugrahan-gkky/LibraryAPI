package com.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN veya MEMBER

    // Rolleri tanımladığımız Enum (Ayrı bir dosyada da durabilir)
    public enum Role {
        ADMIN, // Kütüphane görevlisi (PC uygulamasını kullanan)
        MEMBER // Standart üye
    }
}
