package com.logger.rest.auditserver.service;

import com.logger.rest.auditserver.Events;
import com.logger.rest.auditserver.mediator.MediatorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Value("${gpb.type}")
    private String gpbFramework;

    public String handleEvent(@NonNull Events.Event event) {
        return MediatorFactory.getMediator(gpbFramework)
                .callRpc(event);
    }
}
