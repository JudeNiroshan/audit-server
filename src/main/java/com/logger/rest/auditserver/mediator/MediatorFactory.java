package com.logger.rest.auditserver.mediator;

import com.logger.rest.auditserver.exception.GPBFrameworkNotFound;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MediatorFactory {

    private static final Map<String, Mediator> MEDIATOR_MAP = new HashMap<>();
    private final List<Mediator> mediators;

    public MediatorFactory(List<Mediator> mediators) {
        this.mediators = mediators;
    }

    public static Mediator getMediator(String type) {
        return Optional.ofNullable(MEDIATOR_MAP.get(type))
                .orElseThrow(() -> new GPBFrameworkNotFound("Unknown mediator type: " + type));
    }

    @PostConstruct
    public void initMyServiceCache() {
        for (Mediator mediator : mediators) {
            MEDIATOR_MAP.put(mediator.getType(), mediator);
        }
    }
}
