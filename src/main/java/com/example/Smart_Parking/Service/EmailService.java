package com.example.Smart_Parking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// This service is unoptimized because it is tightly coupled to a single email format
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Your SmartParking Verification Code";
        String text = String.format(
                "Hello,\n\nYour verification code is: %s\n\nPlease enter this code in the app to verify your email.\n\nThis code expires in 10 minutes.\n\nThank you,\nSmartParking Team",
                token
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        
        mailSender.send(message);
    }
}