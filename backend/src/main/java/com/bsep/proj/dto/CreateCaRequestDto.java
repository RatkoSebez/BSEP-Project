package com.bsep.proj.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCaRequestDto {
    private Long ownerId;
    private Long idOfCertificatePublisher;
}
