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

    public void createCertificate(Long idOfCertificatePublisher) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // this two lines creates initial objects, they will get more data in this function
        CertificateAuthority certificateAuthority = createCertificateAuthority(idOfCertificatePublisher);
        Certificate certificate = createCertificate(certificateAuthority, certificateAuthority.getId());
        String hashedCertificateData = CryptographyFunctionsService.hashCertificateData(certificate);

        boolean isRootCa = idOfCertificatePublisher == null;
        CertificateAuthority parentCertificateAuthority;
        String decryptedCertificateData;
        if(isRootCa) {
            certificate.setDigitalSignature(CryptographyFunctionsService.encrypt(certificateAuthority, hashedCertificateData));
            decryptedCertificateData = CryptographyFunctionsService.decrypt(certificateAuthority, certificate.getDigitalSignature());
        }
        else{
            parentCertificateAuthority = certificateAuthorityRepository.getById(idOfCertificatePublisher);
            certificate.setDigitalSignature(CryptographyFunctionsService.encrypt(parentCertificateAuthority ,hashedCertificateData));
            decryptedCertificateData = CryptographyFunctionsService.decrypt(parentCertificateAuthority, certificate.getDigitalSignature());
        }
        certificateAuthority.setCertificate(certificate);

        if(isRootCa)
            System.out.println("SELF SIGNED ---------");
        else
            System.out.println("PARENT SIGNED ---------");
        System.out.println("Hashed dataaaa: " + hashedCertificateData);
        System.out.println("Encrypted data: " + certificate.getDigitalSignature());
        System.out.println("Decrypted data: " + decryptedCertificateData);

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }

    public void createSelfSignedCertificate() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // this two lines creates initial objects, they will get more data in this function
        CertificateAuthority certificateAuthority = createCertificateAuthority(null);
        Certificate certificate = createCertificate(certificateAuthority, certificateAuthority.getId());

        String hashedCertificateData = CryptographyFunctionsService.hashCertificateData(certificate);

        certificate.setDigitalSignature(CryptographyFunctionsService.encrypt(certificateAuthority ,hashedCertificateData));
        certificateAuthority.setCertificate(certificate);

        String decryptedCertificateData = CryptographyFunctionsService.decrypt(certificateAuthority, certificate.getDigitalSignature());

        System.out.println("SELF SIGNED ---------");
        System.out.println("Hashed dataaaa: " + hashedCertificateData);
        System.out.println("Encrypted data: " + certificate.getDigitalSignature());
        System.out.println("Decrypted data: " + decryptedCertificateData);

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }

    public void createCaCertificate(Long idOfCertificatePublisher) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        CertificateAuthority certificateAuthority = createCertificateAuthority(idOfCertificatePublisher);
        Certificate certificate = createCertificate(certificateAuthority, idOfCertificatePublisher);

        String hashedCertificateData = CryptographyFunctionsService.hashCertificateData(certificate);

        CertificateAuthority parentCertificateAuthority = certificateAuthorityRepository.getById(idOfCertificatePublisher);

        certificate.setDigitalSignature(CryptographyFunctionsService.encrypt(parentCertificateAuthority ,hashedCertificateData));
        certificateAuthority.setCertificate(certificate);

        String decryptedCertificateData = CryptographyFunctionsService.decrypt(parentCertificateAuthority, certificate.getDigitalSignature());

        System.out.println("PARENT SIGNED ---------");
        System.out.println("Hashed dataaaa: " + hashedCertificateData);
        System.out.println("Encrypted data: " + certificate.getDigitalSignature());
        System.out.println("Decrypted data: " + decryptedCertificateData);

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }

    private CertificateAuthority createCertificateAuthority(Long idOfCertificatePublisher) throws NoSuchAlgorithmException, NoSuchProviderException {
        CertificateAuthority certificateAuthority = new CertificateAuthority();
        KeyPair keyPair = CryptographyFunctionsService.generateKeys();
        certificateAuthority.setPublicKey(keyPair.getPublic());
        certificateAuthority.setPrivateKey(keyPair.getPrivate());
        certificateAuthority.setCertificateAuthorityParentId(idOfCertificatePublisher); // parent is null = CA is root
        // I save it because id will be assigned, and I need its id
        return certificateAuthorityRepository.save(certificateAuthority);
    }

    private Certificate createCertificate(CertificateAuthority certificateAuthority, Long idOfCertificatePublisher){
        Certificate certificate = new Certificate();
        certificate.setIdOfCertificatePublisher(idOfCertificatePublisher);
        certificate.setIdOfCertificateOwner(certificateAuthority.getId());
        certificate.setTimeOfPublishing(LocalDate.now());
        certificate.setValidUntil(LocalDate.now().plusMonths(6));
        certificate.setIsWithdrawn(false);
        certificate.setPublicKey(certificateAuthority.getPublicKey());
        // I save it because id will be assigned, and I need its id
        return certificateRepository.save(certificate);
    }
}
