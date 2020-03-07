package com.logger.rest.auditserver;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/event")
    @ResponseBody
    public String eventConsumer(@RequestBody String eventJson){

        System.out.println(eventJson);
        return eventService.handleEvent(eventJson);
    }

    @GetMapping(value = "/event")
    @ResponseBody
    public boolean test(){
        System.out.println("json");
        Events.Event myEvent = Events.Event.newBuilder().setTimestamp(LocalDateTime.now().toString())
                .setUser("JJJJJJ")
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
    public boolean tester(){
        System.out.println("json");

        return true;
    }
}
