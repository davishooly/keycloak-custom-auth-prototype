package com.mycompany.keycloak;

import java.time.Instant;

public record VerificationData(
    String phoneNumber,
    String code,
    Instant timestamp,
    int attemptCount
) {
    // Compact constructor for validation
    public VerificationData {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be blank");
        }
        if (code == null || code.length() != 6) {
            throw new IllegalArgumentException("Code must be 6 digits");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (attemptCount < 0) {
            attemptCount = 0;
        }
    }
    
    // Factory method
    public static VerificationData create(String phoneNumber, String code) {
        return new VerificationData(phoneNumber, code, Instant.now(), 0);
    }
    
    // Check if expired (5 minutes)
    public boolean isExpired() {
        return Instant.now().isAfter(timestamp.plusSeconds(300));
    }
    
    // Create new instance with incremented attempts
    public VerificationData incrementAttempts() {
        return new VerificationData(phoneNumber, code, timestamp, attemptCount + 1);
    }
    
    // Check if max attempts reached
    public boolean hasExceededMaxAttempts() {
        return attemptCount >= 3;
    }
}