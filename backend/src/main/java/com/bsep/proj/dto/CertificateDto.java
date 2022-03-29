package com.bsep.proj.dto;

import com.bsep.proj.model.Certificate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto {
    private Long id;
    private Long idOfCertificatePublisher;
    private Long idOfCertificateOwner;
    private LocalDate timeOfPublishing;
    private LocalDate validUntil;
    private Boolean isWithdrawn;
    private byte[] digitalSignature;

    public static CertificateDto convertToCertificateDto(Certificate certificate){
        CertificateDto certificateDto = new CertificateDto();
        certificateDto.setId(certificate.getId());
        certificateDto.setIdOfCertificatePublisher(certificate.getIdOfCertificatePublisher());
        certificateDto.setIdOfCertificateOwner(certificate.getIdOfCertificateOwner());
        certificateDto.setTimeOfPublishing(certificate.getTimeOfPublishing());
        certificateDto.setValidUntil(certificate.getValidUntil());
        certificateDto.setIsWithdrawn(certificate.getIsWithdrawn());
        certificateDto.setDigitalSignature(certificate.getDigitalSignature());
        return certificateDto;
    }

    public static List<CertificateDto> convertToCertificateDtoList(List<Certificate> certificates){
        List<CertificateDto> certificateDtos = new ArrayList<>();
        for(Certificate certificate : certificates){
            certificateDtos.add(convertToCertificateDto(certificate));
        }
        return certificateDtos;
    }
}
