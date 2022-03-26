package com.bsep.proj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String publicKey;
    private Boolean isWithdrawn;
    private String digitalSignature;

    public Certificate(Long idOfPublisher, Long idOfCertificateOwner, LocalDate timeOfPublishing, LocalDate validUntil, String publicKey) {
        this.idOfCertificatePublisher = idOfPublisher;
        this.idOfCertificateOwner = idOfCertificateOwner;
        this.timeOfPublishing = timeOfPublishing;
        this.validUntil = validUntil;
        this.publicKey = publicKey;
    }

    //    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        encoder.encode(this.id.toString() + this.idOfPublisher.toString()
//                + this.idOfCertificateOwner.toString() + this.timeOfPublishing.toString()
//                + this.validUntil.toString() + this.publicKey);
}
