package com.bsep.proj.controller;

import com.bsep.proj.dto.UserDto;
import com.bsep.proj.model.User;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public List<UserDto> getAllUsers(){
        return UserDto.convertToDtoList(userService.getALl());
    }

    @GetMapping("/loggedInUser")
    public UserDto loggedInUser(){
        return UserDto.convertToDto(UserService.getLoggedInUser());
    }

    @GetMapping("/test")
    public String test(){
        return "test works";
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody User user){
        return userService.register(user);
    }
}
