package com.mapper;

import com.dto.response.LoanResponse;
import com.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponse toResponse(Loan loan) {
        if (loan == null) {
            return null;
        }

        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBook() != null ? loan.getBook().getId() : null)
                .bookTitle(loan.getBook() != null ? loan.getBook().getTitle() : null)
                .bookIsbn(loan.getBook() != null ? loan.getBook().getIsbn() : null)
                .userId(loan.getUser() != null ? loan.getUser().getId() : null)
                .userFullName(loan.getUser() != null ? loan.getUser().getFirstName() + " " + loan.getUser().getLastName() : null)
                .userEmail(loan.getUser() != null ? loan.getUser().getEmail() : null)
                .loanDate(loan.getLoanDate())
                .expectedReturnDate(loan.getExpectedReturnDate())
                .actualReturnDate(loan.getActualReturnDate())
                .isReturned(loan.isReturned())
                .build();
    }
}