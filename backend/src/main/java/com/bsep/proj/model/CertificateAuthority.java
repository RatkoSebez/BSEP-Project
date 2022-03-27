package com.bsep.proj.model;

import com.bsep.proj.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.security.Key;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    private Certificate certificate;
    private Long ownerId;
    // private PublicKey publicKey;
    // private PrivateKey privateKey;
    // field is null if it is root CA, otherwise it has id of CA which signed it
    private Long certificateAuthorityParentId;
}
