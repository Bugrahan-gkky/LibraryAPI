package com.service.impl;

import com.entity.Book;
import com.entity.Loan;
import com.entity.User;
import com.repository.BookRepository;
import com.repository.LoanRepository;
import com.repository.UserRepository;
import com.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    // İhtiyacımız olan tüm repository'leri dahil ediyoruz
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public Loan borrowBook(Long userId, Long bookId) {
        // 1. KURAL: Kullanıcı ve Kitap veritabanında var mı?
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı!"));

        // 2. KURAL: Bu kitap şu anda başkasında mı? (Repository'de yazdığımız özel metodu kullanıyoruz)
        boolean isAlreadyBorrowed = loanRepository.existsByBookIdAndIsReturnedFalse(bookId);
        if (isAlreadyBorrowed) {
            throw new RuntimeException("Bu kitap şu an başka bir üyede olduğu için ödünç verilemez!");
        }

        // Tüm kurallar geçildiyse yeni bir ödünç kaydı (Loan) oluştur
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now()); // Veriliş tarihi bugün
        loan.setExpectedReturnDate(LocalDate.now().plusDays(15)); // 15 gün süre veriyoruz
        loan.setReturned(false);

        // Veritabanına kaydet ve döndür
        return loanRepository.save(loan);
    }

    @Override
    public Loan returnBook(Long loanId) {
        // Ödünç kaydını bul
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Ödünç kaydı bulunamadı!"));

        // Zaten iade edilmişse uyar
        if (loan.isReturned()) {
            throw new RuntimeException("Bu kitap zaten iade edilmiş!");
        }

        // İade işlemlerini yap
        loan.setReturned(true);
        loan.setActualReturnDate(LocalDate.now());

        // Güncellenmiş haliyle veritabanına üstüne yaz
        return loanRepository.save(loan);
    }
}
