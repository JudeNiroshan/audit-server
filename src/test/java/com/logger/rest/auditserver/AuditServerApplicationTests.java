package com.logger.rest.auditserver;

import com.logger.rest.auditserver.config.MyTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MyTestConfiguration.class})
class AuditServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
