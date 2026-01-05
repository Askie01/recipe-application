package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.checker.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultCategoryTestPersistenceCheckerTestConfigurationTemplate {

    @Bean
    public CategoryTestPersistenceChecker categoryTestPersistenceChecker(LongIdTestPresenceChecker longIdTestPresenceChecker,
                                                                         StringNameTestPresenceChecker stringNameTestPresenceChecker,
                                                                         SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker,
                                                                         LongVersionTestPresenceChecker longVersionTestPresenceChecker) {
        return new DefaultCategoryTestPersistenceChecker(
                longIdTestPresenceChecker,
                stringNameTestPresenceChecker,
                simpleAuditTestPresenceChecker,
                longVersionTestPresenceChecker
        );
    }
}
