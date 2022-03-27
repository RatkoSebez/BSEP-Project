package com.bsep.proj.controller;

import com.bsep.proj.dto.UserDto;
import com.bsep.proj.model.Certificate;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "ca")
    public void createSelfSignedCertificate() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        certificateService.createCertificateAndCertificateAuthority(null);
        // certificateService.createCertificateAndCertificateAuthority(3l);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "getAll")
    public List<Certificate> getAllCertificates(){
        return certificateRepository.findAll();
    }
}
