package com.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "Kitap adı boş bırakılamaz!")
    private String title;

    @NotBlank(message = "ISBN (Barkod) numarası boş bırakılamaz!")
    private String isbn;

    @Min(value = 1, message = "Stok adedi en az 1 olmalıdır!")
    private int stockCount;

    @NotNull(message = "Yazar seçimi zorunludur!")
    private Long authorId;

    @NotNull(message = "Kategori seçimi zorunludur!")
    private Long categoryId;
}