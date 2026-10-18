package com.service.impl;

import com.dto.response.LoanResponse;
import com.entity.Book;
import com.entity.Loan;
import com.entity.User;
import com.exception.BookOutOfStockException;
import com.exception.BusinessException;
import com.exception.ResourceNotFoundException;
import com.mapper.LoanMapper;
import com.repository.BookRepository;
import com.repository.LoanRepository;
import com.repository.UserRepository;
import com.service.LoanService;
import com.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanMapper loanMapper;

    @Override
    @Transactional
    public LoanResponse borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı! ID: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Kitap bulunamadı! ID: " + bookId));

        boolean isAlreadyBorrowed = loanRepository.existsByBookIdAndIsReturnedFalse(bookId);
        if (isAlreadyBorrowed) {
            throw new BookOutOfStockException("Bu kitap şu an başka bir üyede olduğu için ödünç verilemez!");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setExpectedReturnDate(DateUtils.calculateExpectedReturnDate());
        loan.setReturned(false);

        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toResponse(savedLoan);
    }

    @Override
    @Transactional
    public LoanResponse returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Ödünç kaydı bulunamadı! ID: " + loanId));

        if (loan.isReturned()) {
            throw new BusinessException("Bu kitap zaten iade edilmiş!");
        }

        loan.setReturned(true);
        loan.setActualReturnDate(LocalDate.now());

        Loan savedLoan = loanRepository.save(loan);
        return loanMapper.toResponse(savedLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> getActiveLoansByUser(Long userId) {
        return loanRepository.findByUserIdAndIsReturnedFalse(userId)
                .stream()
                .map(loanMapper::toResponse)
                .collect(Collectors.toList());
    }
}