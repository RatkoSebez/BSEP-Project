package com.bsep.proj.service;

import com.bsep.proj.model.CertificateAuthority;
import com.bsep.proj.model.CertificateAuthorityForTreeView;
import com.bsep.proj.model.LongHolder;
import com.bsep.proj.repository.CertificateAuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TreeViewService {
    private CertificateAuthorityRepository certificateAuthorityRepository;

    public CertificateAuthorityForTreeView getCertificateAuthoritiesForTreeView(){
        CertificateAuthorityForTreeView tree = new CertificateAuthorityForTreeView();
        List<CertificateAuthority> certificateAuthorities = certificateAuthorityRepository.findAll();
        for(CertificateAuthority ca : certificateAuthorities){
            if(ca.getCertificateAuthorityParentId() == null){
                tree.setChildren(findChildren(ca,certificateAuthorities));
                tree.setId(ca.getId());
            }
        }
        return tree;
    }

    private List<CertificateAuthorityForTreeView> findChildren(CertificateAuthority certificateAuthority, List<CertificateAuthority> certificateAuthorities){
        List<CertificateAuthorityForTreeView> children = new ArrayList<>();
        List<LongHolder> childIds = certificateAuthority.getChildren();
        for(CertificateAuthority ca : certificateAuthorities) {
            for (LongHolder childId : childIds) {
                if (ca.getId().equals(childId.getHoldenId())) {
                    children.add(new CertificateAuthorityForTreeView(ca.getId(), findChildren(ca, certificateAuthorities)));
                }
            }
        }
        return children;
    }
}
