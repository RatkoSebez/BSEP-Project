package com.bsep.proj.controller;

import com.bsep.proj.dto.CertificateAuthorityDto;
import com.bsep.proj.dto.CertificateDto;
import com.bsep.proj.dto.CreateRequestDto;
import com.bsep.proj.service.CertificateService;
import com.bsep.proj.service.CreateService;
import com.bsep.proj.service.UserService;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public List<CertificateDto> getAllCertificates(){
        return CertificateDto.convertToDtoList(certificateService.getAll());
    }
}
