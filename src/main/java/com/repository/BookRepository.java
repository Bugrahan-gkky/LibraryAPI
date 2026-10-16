package com.repository;

import com.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    // Belli bir kategori ID'sine ait tüm kitapları listeleyen metod
    List<Book> findByCategoryId(Long categoryId);
}
