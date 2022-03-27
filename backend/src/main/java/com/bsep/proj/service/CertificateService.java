package com.bsep.proj.service;

import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.time.LocalDate;

@AllArgsConstructor
@Service
public class CertificateService {
    private CertificateRepository certificateRepository;
    private CertificateAuthorityRepository certificateAuthorityRepository;

    public void createSelfSignedCertificate() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // this two lines creates initial objects, they will get more data in this function
        CertificateAuthority certificateAuthority = createCertificateAuthority();
        Certificate certificate = createCertificate(certificateAuthority);

        String hashedCertificateData = CryptographyFunctionsService.hashCertificateData(certificate);

        certificate.setDigitalSignature(CryptographyFunctionsService.encrypt(certificateAuthority ,hashedCertificateData));
        certificateAuthority.setCertificate(certificate);

        String decryptedCertificateData = CryptographyFunctionsService.decrypt(certificateAuthority, certificate.getDigitalSignature());

        System.out.println("Hashed dataaaa: " + hashedCertificateData);
        System.out.println("Encrypted data: " + certificate.getDigitalSignature());
        System.out.println("Decrypted data: " + decryptedCertificateData);

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }

    private CertificateAuthority createCertificateAuthority() throws NoSuchAlgorithmException, NoSuchProviderException {
        CertificateAuthority certificateAuthority = new CertificateAuthority();
        KeyPair keyPair = CryptographyFunctionsService.generateKeys();
        certificateAuthority.setPublicKey(keyPair.getPublic());
        certificateAuthority.setPrivateKey(keyPair.getPrivate());
        certificateAuthority.setCertificateAuthorityParentId(null); // parent is null = CA is root
        // I save it because id will be assigned, and I need its id
        return certificateAuthorityRepository.save(certificateAuthority);
    }

    private Certificate createCertificate(CertificateAuthority certificateAuthority){
        Certificate certificate = new Certificate();
        certificate.setIdOfCertificatePublisher(certificateAuthority.getId());
        certificate.setIdOfCertificateOwner(certificateAuthority.getId());
        certificate.setTimeOfPublishing(LocalDate.now());
        certificate.setValidUntil(LocalDate.now().plusMonths(6));
        certificate.setIsWithdrawn(false);
        certificate.setPublicKey(certificateAuthority.getPublicKey());
        // I save it because id will be assigned, and I need its id
        return certificateRepository.save(certificate);
    }
}
