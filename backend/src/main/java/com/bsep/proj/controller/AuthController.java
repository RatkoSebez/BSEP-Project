package com.bsep.proj.controller;

import com.bsep.proj.service.CryptographyFunctionsService;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private CryptographyFunctionsService keyGeneratorService;
    private UserService userService;

//    @GetMapping("/loginn")
//    public String login(){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                "user", "$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra"));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth.getPrincipal().toString();
//    }

    @GetMapping("test")
    public String test() throws NoSuchAlgorithmException, NoSuchProviderException {
        return keyGeneratorService.generateKeys().getPrivate().toString();
    }
}
