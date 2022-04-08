package com.bsep.proj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.PrivateKey;
import java.security.PublicKey;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    private Certificate certificate;
    private Long ownerId;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    // field is null if it is root CA, otherwise it has id of CA which signed it
    private Long certificateAuthorityParentId;
    // ako je true, to znaci da je ovo end entity sertifikat i da ne moze da izdaje druge sertifikate
    // ime klase nije najbolje, probaj naci pogodnije ime
    private Boolean isEndEntityCertificate;
}
