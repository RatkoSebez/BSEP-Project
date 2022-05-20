package com.bsep.proj.service;

import com.bsep.proj.model.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendConfirmationEmail(User user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("isaprojmejl@gmail.com");
        message.setTo(user.getUsername());
        message.setSubject("Please confirm your email address to access bsep website.");
        message.setText("https://localhost/api/user/confirmEmail?code=" + user.getVerificationCode());
        javaMailSender.send(message);
    }
}
