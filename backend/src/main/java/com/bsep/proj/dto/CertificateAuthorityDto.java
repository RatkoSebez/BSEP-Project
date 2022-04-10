package com.bsep.proj.dto;

import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.model.LongHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Boolean isEndEntityCertificate;
    private List<LongHolder> children;

    public static CertificateAuthorityDto convertToDto(CertificateAuthority certificateAuthority){
        CertificateAuthorityDto certificateAuthorityDto = new CertificateAuthorityDto();
        certificateAuthorityDto.setId(certificateAuthority.getId());
        certificateAuthorityDto.setOwnerId(certificateAuthority.getOwnerId());
        certificateAuthorityDto.setCertificateAuthorityParentId(certificateAuthority.getCertificateAuthorityParentId());
        certificateAuthorityDto.setIsEndEntityCertificate(certificateAuthority.getIsEndEntityCertificate());
        certificateAuthorityDto.setChildren(certificateAuthority.getChildren());
        return certificateAuthorityDto;
    }

    public static List<CertificateAuthorityDto> convertToDtoList(List<CertificateAuthority> certificateAuthorities){
        List<CertificateAuthorityDto> certificateAuthorityDtos = new ArrayList<>();
        for(CertificateAuthority certificateAuthority : certificateAuthorities)
            certificateAuthorityDtos.add(convertToDto(certificateAuthority));
        return certificateAuthorityDtos;
    }
}
