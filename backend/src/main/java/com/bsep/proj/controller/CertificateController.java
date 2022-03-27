package com.bsep.proj.controller;

import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.service.CertificateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/certificate")
public class CertificateController {
    private CertificateService certificateService;
    private CertificateAuthorityRepository certificateAuthorityRepository;

    @PostMapping()
    public void createSelfSignedCertificate() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        certificateService.createSelfSignedCertificate();
    }

    @GetMapping()
    public void test(){

    }
}