package com.bsep.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDto {
    private Long ownerId;
    private Long idOfCertificatePublisher;
    private Boolean isEndEntityCertificate;
}