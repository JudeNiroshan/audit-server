package com.logger.rest.auditserver.utils;

import com.netflix.discovery.EurekaClient;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MultiAddressNameResolverFactory extends NameResolver.Factory {
    private final List<EquivalentAddressGroup> addresses;

    public MultiAddressNameResolverFactory(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.addresses = eurekaClient.getApplication("logger-app").getInstances().stream()
                .map(instanceInfo -> new InetSocketAddress(instanceInfo.getHostName(), instanceInfo.getPort()))
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());
    }

    public NameResolver newNameResolver(URI notUsedUri, NameResolver.Args args) {
        return new NameResolver() {
            @Override
            public String getServiceAuthority() {
                return "loggerAppAuthority";
            }

            public void start(Listener2 listener) {
                listener.onResult(ResolutionResult.newBuilder()
                        .setAddresses(addresses)
                        .setAttributes(Attributes.EMPTY)
                        .build());
            }

            public void shutdown() {
            }
        };
    }

    @Override
    public String getDefaultScheme() {
        return "logger";
    }
}
