package com.repository;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA, metodun adından "SELECT * FROM users WHERE email = ?" sorgusunu otomatik üretir.
    Optional<User> findByEmail(String email);

    // E-posta daha önce alınmış mı diye kontrol etmek için
    boolean existsByEmail(String email);
}
