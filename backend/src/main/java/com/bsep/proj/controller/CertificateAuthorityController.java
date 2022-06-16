package com.bsep.proj.controller;

import com.bsep.proj.dto.CertificateAuthorityDto;
import com.bsep.proj.service.CertificateAuthorityService;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/certificateAuthority")
public class CertificateAuthorityController {
    private CertificateAuthorityService certificateAuthorityService;
    private final Logger logger = Logger.getLogger(CertificateAuthorityController.class.getName());

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "")
    public List<CertificateAuthorityDto> getAllCertificateAuthorities(){
        log(null, "getAllCertificateAuthorities()");
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "nonEndEntity")
    public List<CertificateAuthorityDto> getAllNonEndEntityCertificateAuthorities(){
        log(null, "getAllCertificateAuthorities()");
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getAllNonEndEntityCertificateAuthorities());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(path = "user")
    public List<CertificateAuthorityDto> getLoggedInUserCertificateAuthorities(){
        log(null, "getLoggedInUserCertificateAuthorities()");
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getLoggedInUserCertificateAuthorities());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping(path = "userNonEndEntity")
    public List<CertificateAuthorityDto> getUsersNonEndEntityCertificateAuthorities(){
        log(null, "getUsersNonEndEntityCertificateAuthorities()");
        return CertificateAuthorityDto.convertToDtoList(certificateAuthorityService.getLoggedInUserNonEndEntityCertificateAuthorities());
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
