package com.bsep.proj.controller;

import com.bsep.proj.dto.ChangePasswordDto;
import com.bsep.proj.dto.ForgotPasswordDto;
import com.bsep.proj.dto.UserDto;
import com.bsep.proj.model.User;
import com.bsep.proj.repository.UserRepository;
import com.bsep.proj.service.LoggingService;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping(path = "/test")
    public String test(){
        StringBuilder ret = new StringBuilder();
        try{
            FileInputStream fstream = new FileInputStream("logs/spring-boot-logger.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            /* read log line by line */
            while ((strLine = br.readLine()) != null)   {
                /* parse strLine to obtain what you want */
                System.out.println (strLine);
                ret.append(strLine + "\n");
            }
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return ret.toString();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public List<UserDto> getAllUsers(){
        log(null, "getAllUsers()");
        return UserDto.convertToDtoList(userService.getALl());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/loggedInUser")
    public UserDto loggedInUser(){
        log(null, "loggedInUser()");
        return UserDto.convertToDto(UserService.getLoggedInUser());
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody User user){
        User userForLog = new User();
        userForLog.setFirstName(user.getFirstName());
        userForLog.setLastName(user.getLastName());
        userForLog.setUsername(user.getUsername());
        log(userForLog, "register()");
        return userService.register(user);
    }

    @GetMapping(path = "confirmEmail")
    public Boolean confirmEmail(@RequestParam("code") String code){
        log(null, "confirmEmail()");
        User user = userService.getUserByVerificationCode(code);
        if(user == null) return false;
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping("/changePassword")
    public boolean changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        log(null, "changePassword()");
        return userService.changePassword(changePasswordDto);
    }

    @PostMapping("/forgotPassword")
    public boolean forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){
        log(null, "forgotPassword()");
        return userService.forgotPassword(forgotPasswordDto);
    }

    @GetMapping("/sendForgotPasswordVerificationCode/{email}")
    public void sendForgotPasswordVerificationCode(@PathVariable String email){
        log(null, "sendForgotPasswordVerificationCode()");
        userService.sendForgotPasswordVerificationCode(email);
    }

    @GetMapping("/sendPasswordlessLink/{email}")
    public void sendPasswordlessLink(@PathVariable String email){
        log(email, "sendPasswordlessLink()");
        userService.sendPasswordlessLink(email);
    }

    @GetMapping(path = "passwordless")
    public Boolean passwordless(@RequestParam("code") String code){
        User user = userService.getUserByPasswordlessLoginVerificationCode(code);
        if(user == null) return false;
        // this lines does not put user in the session
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private void log(Object obj, String functionName) {
        // log structure: date time log_level class function users_id request_data
        String data = "no_data";
        String userId = "0";
        Long id = UserService.getLoggedInUserId();
        if(obj != null) data = obj.toString();
        if(id != null) userId = id.toString();

        logger.info(functionName + " " + userId + " " + data);
    }
}
