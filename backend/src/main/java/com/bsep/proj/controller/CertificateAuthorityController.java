package com.bsep.proj.controller;

import com.bsep.proj.dto.CertificateAuthorityDto;
import com.bsep.proj.service.CertificateAuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/certificateAuthority")
public class CertificateAuthorityController {
    private CertificateAuthorityService certificateAuthorityService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public List<CertificateAuthorityDto> getAllCertificateAuthorities(){
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "nonEndEntity")
    public List<CertificateAuthorityDto> getAllNonEndEntityCertificateAuthorities(){
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getAllNonEndEntityCertificateAuthorities());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(path = "user")
    public List<CertificateAuthorityDto> getLoggedInUserCertificateAuthorities(){
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getLoggedInUserCertificateAuthorities());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(path = "userNonEndEntity")
    public List<CertificateAuthorityDto> getUsersNonEndEntityCertificateAuthorities(){
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getLoggedInUserNonEndEntityCertificateAuthorities());
    }
}
