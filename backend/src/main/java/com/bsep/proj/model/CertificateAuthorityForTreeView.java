package com.bsep.proj.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CertificateAuthorityForTreeView {
    private Long id;
    private List<CertificateAuthorityForTreeView> children;
}
