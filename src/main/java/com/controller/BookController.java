package com.controller;

import com.dto.request.BookCreateRequest;
import com.dto.response.BookResponse;
import com.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Kitap ekleme: BookCreateRequest alır, BookResponse döner
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookCreateRequest request) {
        BookResponse savedBook = bookService.addBook(request);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Tüm kitapları listeleme: List<BookResponse> döner
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // ID ile kitap getirme: BookResponse döner
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // Kitap silme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}