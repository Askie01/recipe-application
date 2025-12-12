package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultSimpleAuditTestPresenceChecker;
import com.askie01.recipeapplication.checker.SimpleAuditTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultSimpleAuditTestPresenceCheckerTestConfiguration {

    @Bean
    public SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker() {
        return new DefaultSimpleAuditTestPresenceChecker();
    }
}
