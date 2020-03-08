package com.logger.rest.auditserver.mediator;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MediatorFactory {

    private static final Map<String, Mediator> myServiceCache = new HashMap<>();
    private final List<Mediator> services;

    public MediatorFactory(List<Mediator> services) {
        this.services = services;
    }

    public static Mediator getMediator(String type) {
        Mediator service = myServiceCache.get(type);
        if (service == null) throw new RuntimeException("Unknown mediator type: " + type);
        return service;
    }

    @PostConstruct
    public void initMyServiceCache() {
        for (Mediator service : services) {
            myServiceCache.put(service.getType(), service);
        }
    }
}
