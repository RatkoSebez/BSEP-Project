package com.bsep.proj.controller;

import com.bsep.proj.dto.CertificateAuthorityDto;
import com.bsep.proj.dto.CertificateDto;
import com.bsep.proj.dto.CreateCaRequestDto;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.repository.CertificateRepository;
import com.bsep.proj.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/api/certificate")
public class CertificateController {
    private CertificateService certificateService;
    private CertificateRepository certificateRepository;
    private CertificateAuthorityRepository certificateAuthorityRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "createCertificateAuthority")
    public void createCertificateAuthority(@RequestBody CreateCaRequestDto request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        certificateService.createCertificate(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "getAllCertificateAuthorities")
    public List<CertificateAuthorityDto> getAllCertificateAuthorities(){
        // vratio je kad sam izbacio osetljiva polja, uradi to i sa certifikatom
        return CertificateAuthorityDto.convertToCertificateAuthorityDtoList(certificateAuthorityRepository.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "getAllCertificates")
    public List<CertificateDto> getAllCertificates(){
        return CertificateDto.convertToCertificateDtoList(certificateRepository.findAll());
    }
}
