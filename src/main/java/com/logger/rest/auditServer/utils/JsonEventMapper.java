package com.logger.rest.auditServer.utils;

import com.logger.rest.auditServer.Events;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JsonEventMapper {

    public Optional<Events.Event> getEventByJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Object timestamp = jsonObject.get("timestamp");
            Object userId = jsonObject.get("userId");
            String event = jsonObject.getString("event");

            return Optional.of(Events.Event.newBuilder()
                    .setTimestamp(timestamp.toString())
                    .setUser(userId.toString())
                    .setEvent(event)
                    .build());
        } catch (JSONException err) {
            System.out.println("Error" + err.toString());
        }
        return Optional.empty();
    }
}
