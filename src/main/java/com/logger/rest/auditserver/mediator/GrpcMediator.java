package com.logger.rest.auditserver.mediator;

import com.logger.rest.auditserver.EventServiceGrpc;
import com.logger.rest.auditserver.Events;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GrpcMediator implements Mediator {

    private final EurekaClient eurekaClient;
    private final LoadBalancerClient loadBalancerClient;
    private final String target = "127.0.0.1:50051";
    private EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub;

    public GrpcMediator(@Qualifier("eurekaClient") EurekaClient eurekaClient, LoadBalancerClient loadBalancerClient) {
        this.eurekaClient = eurekaClient;
        this.loadBalancerClient = loadBalancerClient;
    }

    @PostConstruct
    private void initCommunicationChannel() {
//        InstanceInfo nextServerInfo = DiscoveryManager.getInstance()
//                .getDiscoveryClient()
//                .getNextServerFromEureka("127.0.0.1", false);

//        ServiceInstance serviceInstance=loadBalancerClient.choose("logger-app"); // mehema karanam sira!
//        URI uri = serviceInstance.getUri();
        List<InstanceInfo> instanceInfoList = eurekaClient.getApplication("logger-app").getInstances();
        InstanceInfo firstInstance = instanceInfoList.get(0);
        String targetString = firstInstance.getHostName() + ":" + firstInstance.getPort();
        ManagedChannel channel = ManagedChannelBuilder.forTarget(targetString).usePlaintext().build();
        eventServiceBlockingStub = EventServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public String getType() {
        return "grpc";
    }

    @Override
    public String callRpc(@NonNull final Events.Event event) {

        Events.LogStatus logStatus = eventServiceBlockingStub.logToFile(event);
        return logStatus.getStatus();
    }
}
