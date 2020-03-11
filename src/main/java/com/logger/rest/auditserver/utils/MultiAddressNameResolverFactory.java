package com.logger.rest.auditserver.utils;

import com.logger.rest.auditserver.exception.RemoteInstancesNotFound;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MultiAddressNameResolverFactory extends NameResolver.Factory {
    private final EurekaClient eurekaClient;
    @Value("${remote.instance.name}")
    private String remoteAppName;

    public MultiAddressNameResolverFactory(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public NameResolver newNameResolver(URI notUsedUri, NameResolver.Args args) {
        return new NameResolver() {
            @Override
            public String getServiceAuthority() {
                return "loggerAppAuthority";
            }

            public void start(Listener2 listener) {
                listener.onResult(ResolutionResult.newBuilder()
                        .setAddresses(fetchAddresses())
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

    private List<EquivalentAddressGroup> fetchAddresses(){
        List<InstanceInfo> instances = getRemoteApplication().getInstances();
        if (instances.size() == 0)
            throw new RemoteInstancesNotFound("Remote instance count is 0");
        return instances.stream()
                .map(instanceInfo -> new InetSocketAddress(instanceInfo.getHostName(), instanceInfo.getPort()))
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());
    }

    private Application getRemoteApplication() {
        Application application = eurekaClient.getApplication(remoteAppName);
        if(application == null)
            throw new RemoteInstancesNotFound(remoteAppName + " is not registered in Eureka server");

        return application;
    }
}
