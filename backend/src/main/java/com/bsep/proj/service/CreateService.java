package com.bsep.proj.service;

import com.bsep.proj.dto.CreateRequestDto;
import com.bsep.proj.model.*;
import com.bsep.proj.model.Certificate;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.time.LocalDate;

import static com.bsep.proj.service.CryptographyFunctionsService.*;

@AllArgsConstructor
@Service
public class CreateService {
    private CertificateRepository certificateRepository;
    private CertificateAuthorityRepository certificateAuthorityRepository;

    public void createCertificateAndCertificateAuthority(CreateRequestDto request) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if (!checkIfRequestIsValid(request)) return;

        CertificateAuthority certificateAuthority = createCertificateAuthority(request);
        Certificate certificate = createCertificateAndCertificateAuthority(certificateAuthority);

        String hashedCertificateData;

        boolean isRootCa = request.getIdOfCertificatePublisher() == null;
        CertificateAuthority parentCertificateAuthority;
        String decryptedCertificateData;
        if (isRootCa) {
            certificate.setIdOfCertificatePublisher(certificateAuthority.getId());
            // this is last place I set something in certificate, so this is time to do hash
            hashedCertificateData = hashCertificateData(certificate);
            certificate.setDigitalSignature(encrypt(certificateAuthority.getPrivateKey(), hashedCertificateData));
            decryptedCertificateData = decrypt(certificateAuthority.getPublicKey(), certificate.getDigitalSignature());
        } else {
            parentCertificateAuthority = certificateAuthorityRepository.getById(request.getIdOfCertificatePublisher());
            parentCertificateAuthority.getChildren().add(new LongHolder(certificateAuthority.getId()));
            certificate.setIdOfCertificatePublisher(parentCertificateAuthority.getId());
            // this is last place I set something in certificate, so this is time to do hash
            hashedCertificateData = hashCertificateData(certificate);
            certificate.setDigitalSignature(encrypt(parentCertificateAuthority.getPrivateKey(), hashedCertificateData));
            decryptedCertificateData = decrypt(parentCertificateAuthority.getPublicKey(), certificate.getDigitalSignature());
        }
        certificateAuthority.setCertificate(certificate);

        if (isRootCa)
            System.out.println("SELF SIGNED ---------");
        else
            System.out.println("PARENT SIGNED ---------");
        System.out.println("Hashed dataaaa: " + hashedCertificateData);
        System.out.println("Encrypted data: " + certificate.getDigitalSignature());
        System.out.println("Decrypted data: " + decryptedCertificateData);

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }

    private CertificateAuthority createCertificateAuthority(CreateRequestDto request) throws NoSuchAlgorithmException, NoSuchProviderException {
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

    private Certificate createCertificateAndCertificateAuthority(CertificateAuthority certificateAuthority) {
        Certificate certificate = new Certificate();
        certificate.setIdOfCertificateOwner(certificateAuthority.getId());
        certificate.setTimeOfPublishing(LocalDate.now());
        certificate.setValidUntil(LocalDate.now().plusMonths(6));
        certificate.setIsWithdrawn(false);
        certificate.setPublicKey(certificateAuthority.getPublicKey());
        // I save it because id will be assigned, and I need its id
        return certificateRepository.save(certificate);
    }

    private Boolean checkIfRequestIsValid(CreateRequestDto request) {
        if (certificateAuthorityIsEndEntity(request.getIdOfCertificatePublisher())) {
            System.out.println("Check error: certificate publisher is end entity.");
            return false;
        }
        if (!clientIsUsingHisCertificateAuthorityForPublishing(request.getIdOfCertificatePublisher())) {
            System.out.println("Check error: client is not using his certificate authority for publishing.");
            return false;
        }
        if (!clientIsCreatingCertificateForHimself(request.getOwnerId())) {
            System.out.println("Check error: client is not creating certificate for himself.");
            return false;
        }
        return true;
    }

    private Boolean certificateAuthorityIsEndEntity(Long publisherId) {
        if(publisherId == null) return false;
        CertificateAuthority certificateAuthority = certificateAuthorityRepository.findById(publisherId).orElseThrow();
        return certificateAuthority.getIsEndEntityCertificate();
    }

    private Boolean clientIsUsingHisCertificateAuthorityForPublishing(Long publisherId) {
        if(publisherId == null) return true;
        User user = UserService.getLoggedInUser();
        if (user.getRole().equals(UserRole.ROLE_ADMIN)) return true;
        CertificateAuthority certificateAuthority = certificateAuthorityRepository.findById(publisherId).orElseThrow();
        return certificateAuthority.getOwnerId().equals(user.getId());
    }

    private Boolean clientIsCreatingCertificateForHimself(Long ownerId) {
        if(ownerId == null) return true;
        User user = UserService.getLoggedInUser();
        if (user.getRole().equals(UserRole.ROLE_ADMIN)) return true;
        return user.getId().equals(ownerId);
    }
}
