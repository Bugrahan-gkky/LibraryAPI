package com.controller;



import com.entity.Loan;
import com.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // PC Uygulaması "Ödünç Ver" butonuna bastığında buraya istek atar
    // POST http://localhost:8080/api/loans/borrow?userId=1&bookId=5
    @PostMapping("/borrow")
    public ResponseEntity<Loan> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        Loan loan = loanService.borrowBook(userId, bookId);
        return ResponseEntity.ok(loan);
    }

    // Kitap iade işlemi
    // POST http://localhost:8080/api/loans/return/10 (Buradaki 10, Loan ID'sidir)
    @PostMapping("/return/{loanId}")
    public ResponseEntity<Loan> returnBook(@PathVariable Long loanId) {
        Loan returnedLoan = loanService.returnBook(loanId);
        return ResponseEntity.ok(returnedLoan);
    }
}
