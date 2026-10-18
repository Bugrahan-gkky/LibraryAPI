package com.service;

import com.dto.response.LoanResponse;
import java.util.List;

public interface LoanService {
    LoanResponse borrowBook(Long userId, Long bookId);
    LoanResponse returnBook(Long loanId);
    List<LoanResponse> getAllLoans();
    List<LoanResponse> getActiveLoansByUser(Long userId);
}