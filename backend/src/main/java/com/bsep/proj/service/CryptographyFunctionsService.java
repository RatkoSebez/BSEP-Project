package com.bsep.proj.service;

import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

@Service
public class CryptographyFunctionsService {
    public static KeyPair generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        // https://docs.oracle.com/javase/tutorial/security/apisign/step2.html
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        return keyGen.generateKeyPair();
    }

    public static byte[] encrypt(CertificateAuthority certificateAuthority, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, certificateAuthority.getPrivateKey());
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(CertificateAuthority certificateAuthority, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, certificateAuthority.getPublicKey());
        return new String(cipher.doFinal(data), StandardCharsets.UTF_8);
    }

    public static String hashCertificateData(Certificate certificate){
        String certificateData = certificate.getId().toString() + certificate.getIdOfCertificatePublisher().toString()
                + certificate.getIdOfCertificateOwner() + certificate.getTimeOfPublishing()
                + certificate.getValidUntil() + certificate.getIsWithdrawn() + certificate.getPublicKey();
        return new BCryptPasswordEncoder().encode(certificateData);
    }
}