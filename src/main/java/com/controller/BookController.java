package com.controller;

import com.entity.Book;
import com.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir REST API olduğunu belirtir
@RequestMapping("/api/books") // Bu sınıftaki tüm metodların ana URL adresi
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // PC Uygulamasından gelen kitap verisini kaydeder (POST http://localhost:8080/api/books)
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED); // 201 Created döner
    }

    // Tüm kitapları liste halinde PC uygulamasına gönderir (GET http://localhost:8080/api/books)
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks()); // 200 OK döner
    }

    // Sadece belirli bir ID'ye sahip kitabı getirir (GET http://localhost:8080/api/books/5)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // Kitabı siler (DELETE http://localhost:8080/api/books/5)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // 204 No Content döner (İşlem başarılı, dönülecek veri yok)
    }
}
