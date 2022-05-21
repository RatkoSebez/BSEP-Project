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

    public void sendEmail(String email, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("isaprojmejl@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
