package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Kayıt Bulunamadı Hatası (404 Not Found döner)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Kayıt Bulunamadı", ex.getMessage());
    }

    // 2. Stok / Ödünç Hatası (400 Bad Request döner)
    @ExceptionHandler(BookOutOfStockException.class)
    public ResponseEntity<Map<String, Object>> handleBookOutOfStockException(BookOutOfStockException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Stok / Ödünç Hatası", ex.getMessage());
    }

    // 3. Genel İş Kuralı Hatası (400 Bad Request döner)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "İşlem Reddedildi", ex.getMessage());
    }

    // 4. Beklenmeyen Sistem Hataları (500 Internal Server Error döner)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // Hatayı konsolda görebilmek için
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Sistem Hatası",
                "Beklenmeyen bir sunucu hatası oluştu: " + ex.getMessage()
        );
    }

    // Kod tekrarını önlemek için JSON cevabını hazırlayan yardımcı metod
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String errorType, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", errorType);
        errorResponse.put("message", message);

        return new ResponseEntity<>(errorResponse, status);
    }
}