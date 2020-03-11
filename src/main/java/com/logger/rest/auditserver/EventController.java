package com.logger.rest.auditserver;

import com.logger.rest.auditserver.utils.EventValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
