package com.bsep.proj.controller;

import com.bsep.proj.dto.UserDto;
import com.bsep.proj.model.User;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public List<UserDto> getAllUsers(){
        return UserDto.convertToDtoList(userService.getALl());
    }

    @GetMapping("/loggedInUser")
    public UserDto loggedInUser(){
        return UserDto.convertToDto(UserService.getLoggedInUser());
    }
}
