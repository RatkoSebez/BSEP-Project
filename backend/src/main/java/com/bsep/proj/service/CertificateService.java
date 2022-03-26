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
        // pravim root CA i dodeljujem mu kljuceve
        KeyPair keyPair = keyGeneratorService.generateKeys();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        CertificateAuthority certificateAuthority = new CertificateAuthority();
        certificateAuthority.setPublicKey(publicKey);
        certificateAuthority.setPrivateKey(privateKey);
        // parent je null i to znaci da je on root, u suprotnom nije root
        certificateAuthority.setCertificateAuthorityParentId(null);
        // cuvam ga da bih mu se dodelio kljuc
        certificateAuthorityRepository.save(certificateAuthority);

        // pravim sertifikat
        Certificate certificate = new Certificate();
        certificate.setIdOfCertificatePublisher(certificateAuthority.getId());
        certificate.setIdOfCertificateOwner(certificateAuthority.getId());
        certificate.setTimeOfPublishing(LocalDate.now());
        certificate.setValidUntil(LocalDate.now().plusMonths(1));
        certificate.setIsWithdrawn(false);
        certificate.setPublicKey(publicKey);
        // moram sacuvati da bi se dodelio id
        certificateRepository.save(certificate);

        // hesovao sam sve podatke unutar sertifikata
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String certificateData = certificate.getId().toString() + certificate.getIdOfCertificatePublisher().toString()
                + certificate.getIdOfCertificateOwner() + certificate.getTimeOfPublishing()
                + certificate.getValidUntil() + certificate.getIsWithdrawn() + certificate.getPublicKey();
        String hashedCertificateData = encoder.encode(certificateData);
        // enkripcija
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, certificateAuthority.getPrivateKey());
        byte[] encryptedCertificateData = cipher.doFinal(hashedCertificateData.getBytes(StandardCharsets.UTF_8));
        certificate.setDigitalSignature(encryptedCertificateData);

        // dekripcija
        cipher.init(Cipher.DECRYPT_MODE, certificateAuthority.getPublicKey());
        byte[] decryptedCertificateData = cipher.doFinal(encryptedCertificateData);

        System.out.println("Hashed data: " + hashedCertificateData);
        System.out.println("Encrypted data: " + encryptedCertificateData);
        System.out.println("Decrypted data: " + Arrays.toString(decryptedCertificateData));

        certificateAuthorityRepository.save(certificateAuthority);
        certificateRepository.save(certificate);
    }
}
