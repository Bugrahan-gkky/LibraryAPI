package com.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class DateUtils {

    private DateUtils() {
    }

    // Bugünün tarihine varsayılan ödünç süresini (15 gün) ekleyip iade tarihini bulur
    public static LocalDate calculateExpectedReturnDate() {
        return LocalDate.now().plusDays(Constants.DEFAULT_LOAN_DAYS);
    }

    // Kitabın iade tarihi geçmiş mi kontrol eder
    public static boolean isOverdue(LocalDate expectedReturnDate) {
        return LocalDate.now().isAfter(expectedReturnDate);
    }

    // Gecikmiş gün sayısını hesaplar (Gecikme yoksa 0 döner)
    public static long getOverdueDays(LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        LocalDate endDate = (actualReturnDate != null) ? actualReturnDate : LocalDate.now();
        if (endDate.isAfter(expectedReturnDate)) {
            return ChronoUnit.DAYS.between(expectedReturnDate, endDate);
        }
        return 0;
    }
}