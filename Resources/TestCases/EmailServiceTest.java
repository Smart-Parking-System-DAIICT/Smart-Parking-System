package com.example.Smart_Parking.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendVerificationEmail_callsMailSenderWithCorrectRecipientAndSubject() {
        
        String to = "user@example.com";
        String token = "123456";

        emailService.sendVerificationEmail(to, token);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();
        assertNotNull(sent);

        String[] toArr = sent.getTo();
        assertNotNull(toArr);
        assertEquals(1, toArr.length);
        assertEquals(to, toArr[0]);

        assertEquals("Your SmartParking Verification Code", sent.getSubject());
    }

    @Test
    void sendVerificationEmail_messageTextContainsTokenAndExpiryNotice() {
        String to = "someone@domain.com";
        String token = "654321";

        emailService.sendVerificationEmail(to, token);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();
        String body = sent.getText();
        assertNotNull(body);

        assertTrue(body.contains(token), "Email body should contain the token");

        assertTrue(body.contains("expires in 10 Min"), "Email body should mention expiry '10 Min'");
        assertTrue(body.contains("Enter this code in the app to verify your email"), "Email body should contain instructions");
    }
}