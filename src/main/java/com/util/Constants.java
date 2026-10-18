package com.util;

public final class Constants {

    // Bu sınıftan "new Constants()" diyerek nesne üretilmesini engelliyoruz
    private Constants() {
    }

    public static final int DEFAULT_LOAN_DAYS = 15;
    public static final int MAX_BOOKS_PER_USER = 3;
    public static final double DAILY_LATE_FEE = 5.0; // Günlük gecikme cezası (TL)

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MEMBER = "MEMBER";
}