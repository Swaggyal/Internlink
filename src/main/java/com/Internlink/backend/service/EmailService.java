package com.Internlink.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("InternLink - Email Verification");
        message.setText("Your OTP code is: " + otp + "\n\nThis code expires in 10 minutes.");
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("InternLink - Reset Your Password");
        message.setText("Click the link below to reset your password:\n" + resetLink + "\n\nThis link expires in 15 minutes.");
        mailSender.send(message);
    }
}

