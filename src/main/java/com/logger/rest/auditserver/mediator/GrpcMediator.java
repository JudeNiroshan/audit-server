package com.logger.rest.auditserver.mediator;

import com.logger.rest.auditserver.EventServiceGrpc;
import com.logger.rest.auditserver.Events;
import com.logger.rest.auditserver.utils.JsonEventMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class GrpcMediator implements Mediator {

    private final String target = "127.0.0.1:50051";
    private final JsonEventMapper jsonEventMapper;
    private EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub;

    public GrpcMediator(JsonEventMapper jsonEventMapper) {
        this.jsonEventMapper = jsonEventMapper;
    }

    @PostConstruct
    private void initCommunicationChannel(){
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        eventServiceBlockingStub = EventServiceGrpc.newBlockingStub(channel);
    }
    @Override
    public String getType() {
        return "grpc";
    }

    @Override
    public String callRpc(String dataAsJsonString) {

        Optional<Events.Event> event = jsonEventMapper.getEventByJson(dataAsJsonString);
        Events.LogStatus logStatus = eventServiceBlockingStub.logToFile(event.get());
        return logStatus.getStatus();
    }
}
