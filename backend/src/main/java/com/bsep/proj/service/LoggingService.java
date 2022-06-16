package com.bsep.proj.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class LoggingService {
    // userId will be 0 for endpoints that require no login
    // data is null if, we know it is GET endpoint
    public void log(Object obj, Logger logger) {
        String data = "no data";
        String userId = "0";
        Long id = UserService.getLoggedInUserId();
        if(obj != null) data = obj.toString();
        if(id != null) userId = id.toString();

        logger.info(userId + " " + data);
    }
}
