package com.logger.rest.auditserver;

import com.logger.rest.auditserver.mediator.MediatorFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public String handleEvent(@NonNull String eventJson) {
        return MediatorFactory.getMediator("grpc")
                .callRpc(eventJson);
    }
}
