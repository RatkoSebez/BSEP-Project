package com.bsep.proj.repository;

import com.bsep.proj.model.CertificateAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateAuthorityRepository extends JpaRepository<CertificateAuthority, Long> {
}
