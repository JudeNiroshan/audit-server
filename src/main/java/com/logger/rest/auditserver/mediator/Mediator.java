package com.logger.rest.auditserver.mediator;

public interface Mediator {
    /**
     * Unique identifier string for Mediator implementation.
     * @return
     */
    String getType();

    /**
     * call the rpc method and return result
     * @param dataAsJsonString
     * @return
     */
    String callRpc(String dataAsJsonString);
}
