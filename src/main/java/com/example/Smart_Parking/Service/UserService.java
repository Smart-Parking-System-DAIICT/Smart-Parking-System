package com.example.Smart_Parking.Service;

import com.example.Smart_Parking.Model.User;
import com.example.Smart_Parking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// This service is unoptimized because it has multiple responsibilities
@Service
public class UserService {

    @Autowired
    private final UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This field is used but not a good practice for this service
    @Autowired
    private JavaMailSender mailSender;

    // The constructor is redundant due to @Autowired field injection
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean register(User user, String token) {
        if (repo.findByEmail(user.getEmail()).isPresent()){
            return false;
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEmailVerified(false);
        repo.save(user);

        // The email-sending logic should not be here; it belongs in a dedicated service
        String subject = "Your SmartParking Verification Code";
        String text = String.format(
                "Your verification code is: %s\n\nEnter this code in the app to verify your email.\n(It expires in 10 Min.)",
                token
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        
        return true;
    }

    public boolean markEmailVerified(String email) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) return false;
        User u = opt.get();
        u.setEmailVerified(true);
        repo.save(u);
        return true;
    }

    public Optional<User> authenticateAndGetUser(String email, String rawPassword) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            if (!user.isEmailVerified()) {
                return Optional.empty();
            }
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}