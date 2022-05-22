package com.bsep.proj.service;

import com.bsep.proj.dto.ChangePasswordDto;
import com.bsep.proj.dto.ForgotPasswordDto;
import com.bsep.proj.model.*;
import com.bsep.proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MESSAGE = "User with username %s not found.";
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    private final ValidationService validationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    public List<User> getALl(){
        return userRepository.findAll();
    }

    public static Long getLoggedInUserId(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) return null;
        User user = (User)principal;
        return user.getId();
    }

    public static User getLoggedInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) return null;
        return (User)principal;
    }

    public ResponseEntity<String> register(User user){
        if(!usernameIsUnique(user.getUsername()))
            return new ResponseEntity<>("email already exists", HttpStatus.CONFLICT);
        if(!validationService.validatePassword(user.getPassword()))
            return new ResponseEntity<>("password is not valid", HttpStatus.BAD_REQUEST);
        if(!validationService.validateEmail(user.getUsername()))
            return new ResponseEntity<>("email is not valid", HttpStatus.BAD_REQUEST);
        user.setPassword(bcrypt.encode(user.getPassword()));
        user.getRole().add(UserRole.ROLE_CLIENT);

        // account is not accessible until email address is confirmed
        user.setEnabled(false);
        String uniqueID = UUID.randomUUID().toString();
        user.setVerificationCode(uniqueID);
        userRepository.save(user);
        String subject = "Please confirm your email address to access bsep website.";
        String text = "https://localhost/api/user/confirmEmail?code=" + user.getVerificationCode();
        emailService.sendEmail(user.getUsername(), subject, text);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public boolean usernameIsUnique(String username){
        return userRepository.findAllByUsername(username).size() <= 0;
    }

    public User getUserByVerificationCode(String verificationCode){
        return userRepository.findByVerificationCode(verificationCode);
    }

    public boolean changePassword(ChangePasswordDto changePasswordDto) {
        User user = getLoggedInUser();
        if(!bcrypt.matches(changePasswordDto.getCurrentPassword(), user.getPassword()))
            return false;
        user.setPassword(bcrypt.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        User user = userRepository.findByForgotPasswordVerificationCode(forgotPasswordDto.getForgotPasswordVerificationCode());
        if(user == null) return false;
        user.setPassword(bcrypt.encode(forgotPasswordDto.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    public void sendForgotPasswordVerificationCode(String email) {
        User user = userRepository.findByUsername(email).get();

        int code = new Random().nextInt(900000) + 100000;
        user.setForgotPasswordVerificationCode(Integer.toString(code));
        userRepository.save(user);

        String subject = "Forgot password verification code.";
        String text = user.getForgotPasswordVerificationCode();
        emailService.sendEmail(email, subject, text);
    }

    public void sendPasswordlessLink(String email) {
        User user = userRepository.findByUsername(email).get();

        int code = new Random().nextInt(900000) + 100000;
        user.setPasswordlessLoginVerificationCode(Integer.toString(code));
        user.setPasswordlessLoginVerificationCodeIssued(new Date());
        userRepository.save(user);

        String subject = "Passwordless login link.";
        String text = "https://localhost/api/user/passwordless?code=" + user.getPasswordlessLoginVerificationCode();
        emailService.sendEmail(email, subject, text);
    }

    public User getUserByPasswordlessLoginVerificationCode(String code) {
        return userRepository.findByPasswordlessLoginVerificationCode(code);
    }
}