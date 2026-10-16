package com.service.impl;

import com.entity.Book;
import com.repository.BookRepository;
import com.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring'e bu sınıfın bir "İş Mantığı" sınıfı olduğunu söyleriz
@RequiredArgsConstructor // Lombok, final olan değişkenler için Constructor'ı otomatik yazar (Dependency Injection)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        // İleride buraya iş kuralları eklenebilir (Örn: Aynı ISBN varsa hata fırlat)
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        // Kitap bulunamazsa hata fırlatıyoruz
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı! ID: " + id));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
