package com.logger.rest.auditserver.mediator;

import com.logger.rest.auditserver.EventServiceGrpc;
import com.logger.rest.auditserver.Events;
import com.logger.rest.auditserver.utils.MultiAddressNameResolverFactory;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GrpcMediator implements Mediator {

    private final MultiAddressNameResolverFactory nameResolverFactory;
    private ManagedChannel channel;
    private EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub;

    public GrpcMediator(MultiAddressNameResolverFactory nameResolverFactory) {
        this.nameResolverFactory = nameResolverFactory;
    }

    @PostConstruct
    private void initCommunicationChannel() {
        channel = ManagedChannelBuilder.forTarget("service")
                .nameResolverFactory(nameResolverFactory)
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();
        eventServiceBlockingStub = EventServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String getType() {
        return "grpc";
    }

    @Override
    public String callRpc(@NonNull final Events.Event event) {
        ensureChannel();
        Events.LogStatus logStatus = eventServiceBlockingStub.logToFile(event);
        return logStatus.getStatus();
    }

    private boolean isChannelActive() {
        return ConnectivityState.READY
                .equals(channel.getState(false));
    }

    private void ensureChannel() {
        if (isChannelActive())
            return;
        initCommunicationChannel();
    }
}
