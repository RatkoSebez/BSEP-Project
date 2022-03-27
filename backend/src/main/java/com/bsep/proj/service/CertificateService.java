package com.bsep.proj.service;

import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import com.bsep.proj.repository.CertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.LocalDate;
import java.util.Arrays;

@AllArgsConstructor
@Service
public class CertificateService {
    private CertificateRepository certificateRepository;
    private KeyGeneratorService keyGeneratorService;
    private CertificateAuthorityRepository certificateAuthorityRepository;

    public void createSelfSignedCertificate() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // this two lines creates initial objects, they will get more data in this function
        CertificateAuthority certificateAuthority = createCertificateAuthority();
        Certificate certificate = createCertificate(certificateAuthority);

        String hashedCertificateData = hashCertificateData(certificate);

        certificate.setDigitalSignature(encrypt(certificateAuthority ,hashedCertificateData));

        String decryptedCertificateData = decrypt(certificateAuthority, certificate.getDigitalSignature());

        System.out.println("Hashed dataaaa: " + hashedCertificateData);
        System.out.println("Encrypted data: " + certificate.getDigitalSignature());
        System.out.println("Decrypted data: " + decryptedCertificateData);

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }

    private CertificateAuthority createCertificateAuthority() throws NoSuchAlgorithmException, NoSuchProviderException {
        CertificateAuthority certificateAuthority = new CertificateAuthority();
        KeyPair keyPair = keyGeneratorService.generateKeys();
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

    private String hashCertificateData(Certificate certificate){
        String certificateData = certificate.getId().toString() + certificate.getIdOfCertificatePublisher().toString()
                + certificate.getIdOfCertificateOwner() + certificate.getTimeOfPublishing()
                + certificate.getValidUntil() + certificate.getIsWithdrawn() + certificate.getPublicKey();
        return new BCryptPasswordEncoder().encode(certificateData);
    }

    private byte[] encrypt(CertificateAuthority certificateAuthority, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, certificateAuthority.getPrivateKey());
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    private String decrypt(CertificateAuthority certificateAuthority, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, certificateAuthority.getPublicKey());
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }
}
