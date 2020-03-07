package com.logger.rest.auditserver;

import com.logger.rest.auditserver.mediator.Mediator;
import com.logger.rest.auditserver.mediator.MediatorFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final MediatorFactory mediatorFactory;

    public EventService(MediatorFactory mediatorFactory) {
        this.mediatorFactory = mediatorFactory;
    }

    public String handleEvent(@NonNull String eventJson) {
        Mediator grpcMediator = MediatorFactory.getMediator("grpc");
        return grpcMediator.callRpc(eventJson);
    }
}
