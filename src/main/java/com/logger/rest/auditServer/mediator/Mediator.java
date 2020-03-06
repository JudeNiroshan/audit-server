package com.logger.rest.auditServer.mediator;

public interface Mediator {
    String getType();
    String callRpc(String dataAsJsonString);
}
