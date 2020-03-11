package com.logger.rest.auditserver.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.logger.rest.auditserver.Events;
import com.logger.rest.auditserver.exception.InvalidDataException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EventValidator {

    public Optional<Events.Event> validate(String jsonString) {
        List<String> errorList = new ArrayList<>();
        Events.Event event;
        try {
            Events.Event.Builder eventBuilder = Events.Event.newBuilder();
            JsonFormat.parser().merge(jsonString, eventBuilder);
            event = eventBuilder.build();
            if (event.getTimestamp() == 0L) {
                errorList.add("Mandatory field 'timestamp' is empty");
            }
            if (event.getUserId() == 0) {
                errorList.add("Mandatory field 'userId' is empty");
            }
            if (StringUtils.isEmpty(event.getEvent())) {
                errorList.add("Mandatory field 'event' is empty");
            }
        } catch (InvalidProtocolBufferException e) {
            throw new InvalidDataException(e.getMessage(), e.getCause());
        }

        if (!errorList.isEmpty()) {
            String allErrors = String.join(" and ", errorList);
            throw new InvalidDataException(allErrors);
        }

        return Optional.of(event);
    }
}
