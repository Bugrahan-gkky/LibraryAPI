package com.service;

import com.entity.Loan;

public interface LoanService {
    Loan borrowBook(Long userId, Long bookId);
    Loan returnBook(Long loanId);
}
