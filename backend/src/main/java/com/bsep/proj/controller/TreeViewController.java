package com.bsep.proj.controller;

import com.bsep.proj.model.CertificateAuthorityForTreeView;
import com.bsep.proj.service.TreeViewService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/treeView")
public class TreeViewController {
    private TreeViewService treeViewService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "treeViewData")
    public CertificateAuthorityForTreeView getCertificateAuthoritiesForTreeView(){
        return treeViewService.getCertificateAuthoritiesForTreeView();
    }
}
