package com.bsep.proj.service;

import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.model.User;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.repository.CertificateRepository;
import com.bsep.proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.time.LocalDate;

import static com.bsep.proj.service.CryptographyFunctionsService.*;
import static com.bsep.proj.service.UserService.*;

@AllArgsConstructor
@Service
public class CertificateService {
    private CertificateRepository certificateRepository;
    private CertificateAuthorityRepository certificateAuthorityRepository;
    private UserRepository userRepository;

    public void createCertificateAndCertificateAuthority(Long idOfCertificatePublisher) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // this two lines creates initial objects, they will get more data in this function
        CertificateAuthority certificateAuthority = createCertificateAuthority(idOfCertificatePublisher);
        Certificate certificate = createCertificateAndCertificateAuthority(certificateAuthority, certificateAuthority.getId());

        String hashedCertificateData = hashCertificateData(certificate);

        boolean isRootCa = idOfCertificatePublisher == null;
        CertificateAuthority parentCertificateAuthority;
        String decryptedCertificateData;
        if(isRootCa) {
            certificate.setDigitalSignature(encrypt(getPrivateKey(certificateAuthority), hashedCertificateData));
            decryptedCertificateData = decrypt(getPublicKey(certificateAuthority), certificate.getDigitalSignature());
        }
        else{
            parentCertificateAuthority = certificateAuthorityRepository.getById(idOfCertificatePublisher);
            certificate.setDigitalSignature(encrypt(getPrivateKey(parentCertificateAuthority), hashedCertificateData));
            decryptedCertificateData = decrypt(getPublicKey(parentCertificateAuthority), certificate.getDigitalSignature());
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

    private CertificateAuthority createCertificateAuthority(Long idOfCertificatePublisher) throws NoSuchAlgorithmException, NoSuchProviderException {
        CertificateAuthority certificateAuthority = new CertificateAuthority();
        KeyPair keyPair = generateKeys();
        certificateAuthority.setCertificateAuthorityParentId(idOfCertificatePublisher); // parent is null = CA is root
        certificateAuthority.setOwnerId(UserService.getLoggedInUserId());
        certificateAuthorityRepository.save(certificateAuthority);
        setPublicKey(certificateAuthority, keyPair.getPublic());
        setPrivateKey(certificateAuthority, keyPair.getPrivate());;
        // I save it because id will be assigned, and I need its id
        return certificateAuthorityRepository.save(certificateAuthority);
    }

    private Certificate createCertificateAndCertificateAuthority(CertificateAuthority certificateAuthority, Long idOfCertificatePublisher){
        Certificate certificate = new Certificate();
        certificate.setIdOfCertificatePublisher(idOfCertificatePublisher);
        certificate.setIdOfCertificateOwner(certificateAuthority.getId());
        certificate.setTimeOfPublishing(LocalDate.now());
        certificate.setValidUntil(LocalDate.now().plusMonths(6));
        certificate.setIsWithdrawn(false);
        certificate.setPublicKey(getPublicKey(certificateAuthority));
        // I save it because id will be assigned, and I need its id
        return certificateRepository.save(certificate);
    }

    private PrivateKey getPrivateKey(CertificateAuthority certificateAuthority){
        User user = userRepository.getById(certificateAuthority.getOwnerId());
        return user.getPrivateKey();
    }

    private PublicKey getPublicKey(CertificateAuthority certificateAuthority){
        User user = userRepository.getById(certificateAuthority.getOwnerId());
        return user.getPublicKey();
    }

    private void setPrivateKey(CertificateAuthority certificateAuthority, PrivateKey privateKey){
        User user = userRepository.getById(certificateAuthority.getOwnerId());
        user.setPrivateKey(privateKey);
        userRepository.save(user);
    }

    private void setPublicKey(CertificateAuthority certificateAuthority, PublicKey publicKey){
        User user = userRepository.getById(certificateAuthority.getOwnerId());
        user.setPublicKey(publicKey);
        userRepository.save(user);
    }
}
