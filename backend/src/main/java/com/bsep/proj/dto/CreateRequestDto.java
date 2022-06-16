package com.bsep.proj.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateRequestDto {
    private Long ownerId;
    private Long idOfCertificatePublisher;
    private Boolean isEndEntityCertificate;
}
