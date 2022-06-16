package com.bsep.proj.controller;

import com.bsep.proj.dto.CertificateAuthorityDto;
import com.bsep.proj.dto.CertificateDto;
import com.bsep.proj.dto.CreateRequestDto;
import com.bsep.proj.dto.RevokedRequestDto;
import com.bsep.proj.service.CertificateService;
import com.bsep.proj.service.CreateService;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/certificate")
public class CertificateController {
    private CertificateService certificateService;
    private final Logger logger = Logger.getLogger(CertificateController.class.getName());

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public List<CertificateDto> getAllCertificates(){
        log(null, "getAllCertificates()");
        return CertificateDto.convertToDtoList(certificateService.getAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(path = "/revoked/{certificateId}")
    public Boolean isRevoked(@PathVariable Long certificateId){
        log(certificateId, "isRevoked()");
        return certificateService.isRevoked(certificateId);
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
