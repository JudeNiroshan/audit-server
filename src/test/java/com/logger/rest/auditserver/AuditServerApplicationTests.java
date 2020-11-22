package com.logger.rest.auditserver;

import com.logger.rest.auditserver.config.MyTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {MyTestConfiguration.class})
@TestPropertySource(locations = "classpath:test-resources.properties")
class AuditServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
