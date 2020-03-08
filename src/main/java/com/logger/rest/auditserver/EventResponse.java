package com.logger.rest.auditserver;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class EventResponse {
    private String logStatus;
}
