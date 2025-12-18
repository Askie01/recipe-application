package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.checker.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultIngredientTestPersistenceCheckerTestConfigurationTemplate {

    @Bean
    public IngredientTestPersistenceChecker ingredientTestPersistenceChecker(LongIdTestPresenceChecker longIdTestPresenceChecker,
                                                                             StringNameTestPresenceChecker stringNameTestPresenceChecker,
                                                                             AmountTestPresenceChecker amountTestPresenceChecker,
                                                                             MeasureUnitTestPersistenceChecker measureUnitTestPersistenceChecker,
                                                                             SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker,
                                                                             LongVersionTestPresenceChecker longVersionTestPresenceChecker) {
        return new DefaultIngredientTestPersistenceChecker(
                longIdTestPresenceChecker,
                stringNameTestPresenceChecker,
                amountTestPresenceChecker,
                measureUnitTestPersistenceChecker,
                simpleAuditTestPresenceChecker,
                longVersionTestPresenceChecker
        );
    }
}
