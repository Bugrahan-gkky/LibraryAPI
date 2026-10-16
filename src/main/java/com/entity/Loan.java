package com.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate loanDate; // Verildiği tarih

    private LocalDate expectedReturnDate; // Beklenen iade tarihi

    private LocalDate actualReturnDate; // Gerçekte iade edildiği tarih

    @Column(nullable = false)
    private boolean isReturned = false; // İade edildi mi? (Varsayılan: Hayır)
}
