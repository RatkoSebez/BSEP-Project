package com.bsep.proj.controller;

import com.bsep.proj.dto.CreateRequestDto;
import com.bsep.proj.service.CreateService;
import com.bsep.proj.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.logging.Logger;

@AllArgsConstructor
@Controller
@RestController
@RequestMapping(value = "/api/create")
public class CreateController {
    private CreateService createService;
    private final Logger logger = Logger.getLogger(CreateController.class.getName());

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping()
    public void createCertificateAuthority(@RequestBody CreateRequestDto request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        log(request, "createCertificateAuthority()");
        createService.createCertificateAndCertificateAuthority(request);
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
