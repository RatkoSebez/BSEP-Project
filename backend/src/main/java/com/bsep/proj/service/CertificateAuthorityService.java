package com.bsep.proj.service;

import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CertificateAuthorityService {
    private CertificateAuthorityRepository certificateAuthorityRepository;

    public List<CertificateAuthority> getAll(){
        return certificateAuthorityRepository.findAll();
    }

    public List<CertificateAuthority> getAllNonEndEntityCertificateAuthorities(){
        List<CertificateAuthority> certificateAuthorities = new ArrayList<>();
        List<CertificateAuthority> allCa = certificateAuthorityRepository.findAll();
        for(CertificateAuthority certificateAuthority : allCa){
            if(!certificateAuthority.getIsEndEntityCertificate()) certificateAuthorities.add(certificateAuthority);
        }
        return certificateAuthorities;
    }

    public List<CertificateAuthority> getLoggedInUserCertificateAuthorities(){
        List<CertificateAuthority> certificateAuthorities = new ArrayList<>();
        List<CertificateAuthority> allCa = certificateAuthorityRepository.findAll();
        Long ownerId = UserService.getLoggedInUserId();
        for(CertificateAuthority certificateAuthority : allCa){
            if(certificateAuthority.getOwnerId() == ownerId) certificateAuthorities.add(certificateAuthority);
        }
        return certificateAuthorities;
    }

//    public List<Certificate> getLoggedInUserUserCertificates(){
//        List<CertificateAuthority> certificateAuthorities = getLoggedInUserCertificateAuthorities();
//        List<Certificate> certificates = new ArrayList<>();
//        for(CertificateAuthority certificateAuthority : certificateAuthorities){
//            certificates.add(certificateAuthority.getCertificate());
//        }
//        return certificates;
//    }

    public List<CertificateAuthority> getLoggedInUserNonEndEntityCertificateAuthorities(){
        List<CertificateAuthority> certificateAuthorities = new ArrayList<>();
        List<CertificateAuthority> allCa = certificateAuthorityRepository.findAll();
        Long ownerId = UserService.getLoggedInUserId();
        for(CertificateAuthority certificateAuthority : allCa){
            if(certificateAuthority.getOwnerId() == ownerId && !certificateAuthority.getIsEndEntityCertificate()) certificateAuthorities.add(certificateAuthority);
        }
        return certificateAuthorities;
    }
}
