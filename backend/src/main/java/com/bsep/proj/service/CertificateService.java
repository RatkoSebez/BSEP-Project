package com.bsep.proj.service;

import com.bsep.proj.dto.CreateCaRequestDto;
import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.model.LongHolder;
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
import java.util.ArrayList;

import static com.bsep.proj.service.CryptographyFunctionsService.*;

@AllArgsConstructor
@Service
public class CertificateService {
    private CertificateRepository certificateRepository;
    private CertificateAuthorityRepository certificateAuthorityRepository;
    private UserRepository userRepository;

    public void createCertificate(CreateCaRequestDto request) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // uraditi provere za objekat request
        // sertifikat moze da pablisuje jedino intermediate i root ca
        // user moze da koristi jedino svoj ca za pablisovanje
        // user moze da pravi sertifikate samo za sebe

        // mozda baci neki exception, vidi sta ako ne valja request
        if(!checkIfRequestIsValid()) return;

        CertificateAuthority certificateAuthority = createCertificateAuthority(request);
        Certificate certificate = createCertificate(certificateAuthority);

        String hashedCertificateData;

        boolean isRootCa = request.getIdOfCertificatePublisher() == null;
        CertificateAuthority parentCertificateAuthority;
        String decryptedCertificateData;
        if(isRootCa) {
            certificate.setIdOfCertificatePublisher(certificateAuthority.getId());
            // this is last place I set something in certificate, so this is time to do hash
            hashedCertificateData = hashCertificateData(certificate);
            certificate.setDigitalSignature(encrypt(certificateAuthority.getPrivateKey(), hashedCertificateData));
            decryptedCertificateData = decrypt(certificateAuthority.getPublicKey(), certificate.getDigitalSignature());
        }
        else{
            parentCertificateAuthority = certificateAuthorityRepository.getById(request.getIdOfCertificatePublisher());
            parentCertificateAuthority.getChildren().add(new LongHolder(certificateAuthority.getId()));
            certificate.setIdOfCertificatePublisher(parentCertificateAuthority.getId());
            // this is last place I set something in certificate, so this is time to do hash
            hashedCertificateData = hashCertificateData(certificate);
            certificate.setDigitalSignature(encrypt(parentCertificateAuthority.getPrivateKey(), hashedCertificateData));
            decryptedCertificateData = decrypt(parentCertificateAuthority.getPublicKey(), certificate.getDigitalSignature());
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

    private CertificateAuthority createCertificateAuthority(CreateCaRequestDto request) throws NoSuchAlgorithmException, NoSuchProviderException {
        CertificateAuthority certificateAuthority = new CertificateAuthority();
        KeyPair keyPair = generateKeys();
        certificateAuthority.setCertificateAuthorityParentId(request.getIdOfCertificatePublisher()); // parent is null = CA is root
        certificateAuthority.setOwnerId(request.getOwnerId());
        certificateAuthorityRepository.save(certificateAuthority);
        certificateAuthority.setPublicKey(keyPair.getPublic());
        certificateAuthority.setPrivateKey(keyPair.getPrivate());
        certificateAuthority.setIsEndEntityCertificate(request.getIsEndEntityCertificate());
        // I save it because id will be assigned, and I need its id
        return certificateAuthorityRepository.save(certificateAuthority);
    }

    private Certificate createCertificate(CertificateAuthority certificateAuthority){
        Certificate certificate = new Certificate();
        certificate.setIdOfCertificateOwner(certificateAuthority.getId());
        certificate.setTimeOfPublishing(LocalDate.now());
        certificate.setValidUntil(LocalDate.now().plusMonths(6));
        certificate.setIsWithdrawn(false);
        certificate.setPublicKey(certificateAuthority.getPublicKey());
        // I save it because id will be assigned, and I need its id
        return certificateRepository.save(certificate);
    }

    private Boolean checkIfRequestIsValid(){
        // TODO
        return true;
    }

//    private PrivateKey getPrivateKey(CertificateAuthority certificateAuthority){
//        User user = userRepository.getById(certificateAuthority.getOwnerId());
//        return user.getPrivateKey();
//    }
//
//    private PublicKey getPublicKey(CertificateAuthority certificateAuthority){
//        User user = userRepository.getById(certificateAuthority.getOwnerId());
//        return user.getPublicKey();
//    }
//
//    private void setPrivateKey(CertificateAuthority certificateAuthority, PrivateKey privateKey){
//        User user = userRepository.getById(certificateAuthority.getOwnerId());
//        user.setPrivateKey(privateKey);
//        userRepository.save(user);
//    }
//
//    private void setPublicKey(CertificateAuthority certificateAuthority, PublicKey publicKey){
//        User user = userRepository.getById(certificateAuthority.getOwnerId());
//        user.setPublicKey(publicKey);
//        userRepository.save(user);
//    }
}
