package com.bsep.proj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.PublicKey;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // ako su publisher i owner isti onda znam da je to sertifikat od CA-ja
    private Long idOfCertificatePublisher;
    private Long idOfCertificateOwner;
    private LocalDate timeOfPublishing;
    private LocalDate validUntil;
    private PublicKey publicKey;
    private byte[] digitalSignature;
    private Boolean isRevoked;
}
