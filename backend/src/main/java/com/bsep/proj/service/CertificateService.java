package com.bsep.proj.service;

import com.bsep.proj.dto.CertificateDto;
import com.bsep.proj.dto.RevokedRequestDto;
import com.bsep.proj.model.Certificate;
import com.bsep.proj.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CertificateService {
    private CertificateRepository certificateRepository;

    public List<Certificate> getAll(){
        return certificateRepository.findAll();
    }

    public Boolean isRevoked(Long certificateId){
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow();
        return certificate.getIsRevoked();
    }
}
