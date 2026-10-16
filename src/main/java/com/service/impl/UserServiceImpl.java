package com.service.impl;

import com.entity.User;
import com.repository.UserRepository;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        // KURAL: Aynı e-posta ile ikinci bir kayıt oluşturulamaz.
        // Repository'de yazdığımız "existsByEmail" metodunu kullanıyoruz.
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu e-posta adresi sistemde zaten kayıtlı!");
        }

        // ÖNEMLİ NOT: Şu an şifreyi düz metin olarak kaydediyoruz.
        // İleride Security katmanını yazdığımızda burada user.setPassword(passwordEncoder.encode(user.getPassword())) yapacağız.

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı! ID: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı! Email: " + email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
