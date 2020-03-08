package com.logger.rest.auditserver;

import com.logger.rest.auditserver.utils.EventValidator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(path = "/user")
public class EventController {

    private final EventValidator eventValidator;
    private final EventService eventService;

    public EventController(EventValidator eventValidator, EventService eventService) {
        this.eventValidator = eventValidator;
        this.eventService = eventService;
    }

    @PostMapping(value = "/event", headers = "Accept=application/json")
    @ResponseBody
    public EventResponse eventConsumer(@RequestBody String eventJson) {

        return eventValidator.validate(eventJson)
                .map(eventService::handleEvent)
                .map(EventResponse::new).get();

    }

    @GetMapping(value = "/event")
    @ResponseBody
    public boolean test() {
        // validations needs to be done
//        throw new ValidationException("no idea");
        System.out.println("json");
        Events.Event myEvent = Events.Event.newBuilder().setTimestamp(Instant.now().toEpochMilli())
                .setUserId(678)
                .setEvent("some log bro!!!").build();
        ManagedChannel channel = ManagedChannelBuilder.forTarget("127.0.0.1:50051").usePlaintext()
                .build();
        EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub = EventServiceGrpc.newBlockingStub(channel);
        Events.LogStatus logStatus = eventServiceBlockingStub.logToFile(myEvent);
        System.out.println("logStatus" + logStatus);
        return true;
    }

    @GetMapping(value = "/not")
    @ResponseBody
    public boolean tester() {
        System.out.println("json");

        return true;
    }
}
