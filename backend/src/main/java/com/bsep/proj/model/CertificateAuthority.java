package com.bsep.proj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
//    private Certificate certificateOfCA;
//    private List<Certificate> publishedCertificates;
    private String privateKey;
    private Long certificateAuthorityParentId;

//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        encoder.encode(this.id.toString() + this.idOfPublisher.toString()
//                + this.idOfCertificateOwner.toString() + this.timeOfPublishing.toString()
//                + this.validUntil.toString() + this.publicKey);
}
