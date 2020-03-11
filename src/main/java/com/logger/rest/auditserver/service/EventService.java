package com.logger.rest.auditserver.service;

import com.logger.rest.auditserver.Events;
import com.logger.rest.auditserver.mediator.MediatorFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public String handleEvent(@NonNull Events.Event event) {
        return MediatorFactory.getMediator("grpc")
                .callRpc(event);
    }
}
