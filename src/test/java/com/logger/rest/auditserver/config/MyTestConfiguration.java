package com.logger.rest.auditserver.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@TestConfiguration
public class MyTestConfiguration {

    @Bean
    @Qualifier("eurekaClient")
    public EurekaClient getEurekaClient() {
        EurekaClient eurekaClientMock = Mockito.mock(EurekaClient.class);
        Application applicationMock = Mockito.mock(Application.class);
        InstanceInfo instanceInfoMock = Mockito.mock(InstanceInfo.class);
        String testHostName = "localhost";
        int testPort = 50089;
        Mockito.when(instanceInfoMock.getHostName()).thenReturn(testHostName);
        Mockito.when(instanceInfoMock.getPort()).thenReturn(testPort);

        Mockito.when(applicationMock.getInstances()).thenReturn(Collections.singletonList(instanceInfoMock));
        Mockito.when(eurekaClientMock.getApplication(Mockito.anyString())).thenReturn(applicationMock);

        return eurekaClientMock;
    }
}
