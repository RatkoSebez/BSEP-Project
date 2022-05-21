package com.bsep.proj.controller;

import com.bsep.proj.dto.ChangePasswordDto;
import com.bsep.proj.dto.ForgotPasswordDto;
import com.bsep.proj.dto.UserDto;
import com.bsep.proj.model.User;
import com.bsep.proj.repository.UserRepository;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public List<UserDto> getAllUsers(){
        return UserDto.convertToDtoList(userService.getALl());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/loggedInUser")
    public UserDto loggedInUser(){
        return UserDto.convertToDto(UserService.getLoggedInUser());
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping(path = "confirmEmail")
    public Boolean confirmEmail(@RequestParam("code") String code){
        User user = userService.getUserByVerificationCode(code);
        if(user == null) return false;
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping("/changePassword")
    public boolean changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return userService.changePassword(changePasswordDto);
    }

    @PostMapping("/forgotPassword")
    public boolean forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){
        return userService.forgotPassword(forgotPasswordDto);
    }

    @GetMapping("/sendForgotPasswordVerificationCode/{email}")
    public void sendForgotPasswordVerificationCode(@PathVariable String email){
        userService.sendForgotPasswordVerificationCode(email);
    }
}
