package com.bsep.proj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    private Certificate certificate;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Long certificateAuthorityParentId;
}
