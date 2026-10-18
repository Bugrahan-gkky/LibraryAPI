package com.mapper;

import com.dto.request.BookCreateRequest;
import com.dto.response.BookResponse;
import com.entity.Author;
import com.entity.Book;
import com.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // 1. Entity -> Response DTO (Veritabanından PC Uygulamasına giderken)
    public BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .stockCount(book.getStockCount())
                // Yazar ve Kategori nesnelerinin sadece isimlerini alıp düz metin olarak koyuyoruz
                .authorName(book.getAuthor() != null ? book.getAuthor().getFullName() : null)
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .build();
    }

    // 2. Request DTO -> Entity (PC Uygulamasından Veritabanına kaydedilirken)
    public Book toEntity(BookCreateRequest request, Author author, Category category) {
        if (request == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setStockCount(request.getStockCount());
        book.setAuthor(author);     // Servis katmanında ID'den bulduğumuz yazarı bağlıyoruz
        book.setCategory(category); // Servis katmanında ID'den bulduğumuz kategoriyi bağlıyoruz

        return book;
    }
}