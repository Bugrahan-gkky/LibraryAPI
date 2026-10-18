package com.controller;

import com.dto.request.LoanRequest;
import com.dto.response.LoanResponse;
import com.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // Kitap ödünç ver (POST http://localhost:8080/api/loans/borrow)
    @PostMapping("/borrow")
    public ResponseEntity<LoanResponse> borrowBook(@Valid @RequestBody LoanRequest request) {
        return ResponseEntity.ok(loanService.borrowBook(request.getUserId(), request.getBookId()));
    }

    // Kitap iade al (POST http://localhost:8080/api/loans/return/1)
    @PostMapping("/return/{loanId}")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }

    // Tüm ödünç geçmişini listele (GET http://localhost:8080/api/loans)
    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    // Bir kullanıcının elindeki aktif kitapları listele (GET http://localhost:8080/api/loans/user/1)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponse>> getActiveLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getActiveLoansByUser(userId));
    }
}