package com.logger.rest.auditserver.service;

import com.logger.rest.auditserver.Events;
import com.logger.rest.auditserver.exception.GlobalExceptionHandler;
import com.logger.rest.auditserver.mediator.MediatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${gpb.type}")
    private String gpbFramework;

    public String handleEvent(@NonNull Events.Event event) {
        try {
            return MediatorFactory.getMediator(gpbFramework)
                    .callRpc(event);
        } catch (Exception e) {
            logger.trace(event.toString(), e);
            throw e;
        }
    }
}
