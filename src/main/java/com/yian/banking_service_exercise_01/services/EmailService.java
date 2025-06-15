package com.yian.banking_service_exercise_01.services;


public interface EmailService {
    void sendEmail(
            String email,
            String username,
            String subject
    );
    void sendEmailVerificationCode(String to, String code);
}
