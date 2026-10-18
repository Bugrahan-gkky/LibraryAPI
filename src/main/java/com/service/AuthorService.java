package com.service;

import com.entity.Author;
import java.util.List;

public interface AuthorService {
    Author createAuthor(Author author);
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    void deleteAuthor(Long id);
}