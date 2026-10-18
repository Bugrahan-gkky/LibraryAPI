package com.service.impl;

import com.dto.request.BookCreateRequest;
import com.dto.response.BookResponse;
import com.entity.Author;
import com.entity.Book;
import com.entity.Category;
import com.exception.ResourceNotFoundException;
import com.mapper.BookMapper;
import com.repository.AuthorRepository;
import com.repository.BookRepository;
import com.repository.CategoryRepository;
import com.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponse addBook(BookCreateRequest request) {
        // 1. DTO'dan gelen ID'ler ile Yazar ve Kategoriyi veritabanından bul
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Yazar bulunamadı! ID: " + request.getAuthorId()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori bulunamadı! ID: " + request.getCategoryId()));

        // 2. Mapper ile Request DTO'yu Entity'ye çevir ve kaydet
        Book book = bookMapper.toEntity(request, author, category);
        Book savedBook = bookRepository.save(book);

        // 3. Kaydedilen Entity'yi tekrar güvenli Response DTO'ya çevirip dön
        return bookMapper.toResponse(savedBook);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        // Tüm kitapları çekip Stream API ve Mapper ile BookResponse listesine dönüştürüyoruz
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı! ID: " + id));
        return bookMapper.toResponse(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Silinecek kitap bulunamadı! ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}