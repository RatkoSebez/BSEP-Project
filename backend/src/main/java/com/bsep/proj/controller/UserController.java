package com.bsep.proj.controller;

import com.bsep.proj.dto.UserDto;
import com.bsep.proj.model.User;
import com.bsep.proj.repository.UserRepository;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "getAll")
    public List<UserDto> getAllUsers(){
        return UserDto.convertToUserDtoList(userRepository.findAll());
    }

    @GetMapping("/loggedInUser")
    public UserDto loggedInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String) return null;
        return UserDto.convertToUserDto((User)principal);
    }
}
