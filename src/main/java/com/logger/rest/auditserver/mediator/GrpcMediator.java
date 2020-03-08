package com.logger.rest.auditserver.mediator;

import com.logger.rest.auditserver.EventServiceGrpc;
import com.logger.rest.auditserver.Events;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GrpcMediator implements Mediator {

    private final String target = "127.0.0.1:50051";
    private EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub;

    @PostConstruct
    private void initCommunicationChannel() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        eventServiceBlockingStub = EventServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String getType() {
        return "grpc";
    }

    @Override
    public String callRpc(@NonNull final Events.Event event) {

        Events.LogStatus logStatus = eventServiceBlockingStub.logToFile(event);
        return logStatus.getStatus();
    }
}
