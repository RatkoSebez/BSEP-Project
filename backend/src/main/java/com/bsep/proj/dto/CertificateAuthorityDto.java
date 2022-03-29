package com.bsep.proj.dto;

import com.bsep.proj.model.Certificate;
import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateAuthorityDto {
    private Long id;
    private Long ownerId;
    private Long certificateAuthorityParentId;

    public static CertificateAuthorityDto convertToCertificateAuthorityDto(CertificateAuthority certificateAuthority){
        CertificateAuthorityDto certificateAuthorityDto = new CertificateAuthorityDto();
        certificateAuthorityDto.setId(certificateAuthority.getId());
        certificateAuthorityDto.setOwnerId(certificateAuthority.getOwnerId());
        certificateAuthorityDto.setCertificateAuthorityParentId(certificateAuthority.getCertificateAuthorityParentId());
        return certificateAuthorityDto;
    }

    public static List<CertificateAuthorityDto> convertToCertificateAuthorityDtoList(List<CertificateAuthority> certificateAuthorities){
        List<CertificateAuthorityDto> certificateAuthorityDtos = new ArrayList<>();
        for(CertificateAuthority certificateAuthority : certificateAuthorities){
            certificateAuthorityDtos.add(convertToCertificateAuthorityDto(certificateAuthority));
        }
        return certificateAuthorityDtos;
    }
}
