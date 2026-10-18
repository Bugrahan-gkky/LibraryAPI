package com.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

    @NotNull(message = "Kullanıcı ID boş olamaz!")
    private Long userId;

    @NotNull(message = "Kitap ID boş olamaz!")
    private Long bookId;
}