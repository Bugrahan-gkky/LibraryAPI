package     com.repository;

import com.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Bir kullanıcının henüz İADE ETMEDİĞİ ödünç aldığı kitapları getirir
    // SQL: SELECT * FROM loans WHERE user_id = ? AND is_returned = false
    List<Loan> findByUserIdAndIsReturnedFalse(Long userId);

    // Belirli bir kitabın şu an birisinde olup olmadığını kontrol eder
    boolean existsByBookIdAndIsReturnedFalse(Long bookId);
}
