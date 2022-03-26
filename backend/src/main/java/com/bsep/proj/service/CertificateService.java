package com.bsep.proj.service;

import com.bsep.proj.model.Certificate;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {
    public Certificate createSelfSignedCertificate(){
        Certificate certificate = new Certificate();

        return certificate;
    }
}
