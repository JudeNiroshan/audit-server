package com.logger.rest.auditserver.mediator;

import com.logger.rest.auditserver.Events;
import org.springframework.lang.NonNull;

public interface Mediator {
    /**
     * Unique identifier string for Mediator implementation.
     * @return
     */
    String getType();

    /**
     * call the rpc method and return result
     * @param event
     * @return
     */
    String callRpc(@NonNull final Events.Event event);
}
