package com.bsep.proj.service;

import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.model.User;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.repository.CertificateRepository;
import com.bsep.proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MESSAGE = "User with username %s not found.";
    private final UserRepository userRepository;
    private CertificateAuthorityRepository certificateAuthorityRepository;
    private CertificateRepository certificateRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
    }

    public static String getLoggedInUserUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof User) {
            username = ((User)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public static Long getLoggedInUserId(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    public static User getLoggedInUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<CertificateAuthority> getUserCertificateAuthorities(){
        List<CertificateAuthority> certificateAuthorities = new ArrayList<>();
        List<CertificateAuthority> allCa = certificateAuthorityRepository.findAll();
        Long ownerId = getLoggedInUserId();
        for(CertificateAuthority certificateAuthority : allCa){
            if(certificateAuthority.getOwnerId() == ownerId) certificateAuthorities.add(certificateAuthority);
        }
        return certificateAuthorities;
    }

    public List<Certificate> getUserCertificates(){
        List<CertificateAuthority> certificateAuthorities = getUserCertificateAuthorities();
        List<Certificate> certificates = new ArrayList<>();
        for(CertificateAuthority certificateAuthority : certificateAuthorities){
            certificates.add(certificateAuthority.getCertificate());
        }
        return certificates;
    }
}
