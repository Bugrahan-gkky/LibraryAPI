package com.mapper;

import com.dto.response.UserResponse;
import com.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Entity -> Response DTO (Şifreyi gizleyerek PC uygulamasına kullanıcı bilgisi döner)
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}