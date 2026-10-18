package com.service;

import com.dto.request.BookCreateRequest;
import com.dto.response.BookResponse;
import java.util.List;

public interface BookService {
    BookResponse addBook(BookCreateRequest request);
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    void deleteBook(Long id);
}