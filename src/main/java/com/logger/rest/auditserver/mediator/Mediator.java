package com.logger.rest.auditserver.mediator;

public interface Mediator {
    String getType();
    String callRpc(String dataAsJsonString);
}
