package com.bsep.proj.service;

import org.springframework.stereotype.Service;

import java.security.*;

@Service
public class KeyGeneratorService {
    public KeyPair generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        // https://docs.oracle.com/javase/tutorial/security/apisign/step2.html
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        return pair;
    }
}
