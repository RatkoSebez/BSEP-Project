package com.bsep.proj.controller;

import com.bsep.proj.model.CertificateAuthorityForTreeView;
import com.bsep.proj.service.TreeViewService;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/treeView")
public class TreeViewController {
    private TreeViewService treeViewService;
    private final Logger logger = Logger.getLogger(UserController.class.getName());

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public CertificateAuthorityForTreeView getCertificateAuthoritiesForTreeView(){
        log(null, "getCertificateAuthoritiesForTreeView()");
        return treeViewService.getCertificateAuthoritiesForTreeView();
    }

    private void log(Object obj, String functionName) {
        // log structure: date time log_level class function users_id request_data
        String data = "no_data";
        String userId = "0";
        Long id = UserService.getLoggedInUserId();
        if(obj != null) data = obj.toString();
        if(id != null) userId = id.toString();

        logger.info(functionName + " " + userId + " " + data);
    }
}
