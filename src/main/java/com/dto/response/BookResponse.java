package com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private int stockCount;
    private String authorName;   // Author nesnesi yerine sadece tam adı
    private String categoryName; // Category nesnesi yerine sadece kategori adı
}