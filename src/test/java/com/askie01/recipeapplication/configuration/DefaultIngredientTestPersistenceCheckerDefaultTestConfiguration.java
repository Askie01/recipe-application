package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.DefaultIngredientTestPersistenceCheckerTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        DefaultLongIdTestPresenceCheckerTestConfiguration.class,
        DefaultStringNameTestPresenceCheckerTestConfiguration.class,
        DefaultAmountTestPresenceCheckerTestConfiguration.class,
        DefaultMeasureUnitTestPersistenceCheckerDefaultTestConfiguration.class,
        DefaultSimpleAuditTestPresenceCheckerTestConfiguration.class,
        DefaultLongVersionTestPresenceCheckerTestConfiguration.class
})
public class DefaultIngredientTestPersistenceCheckerDefaultTestConfiguration
        extends DefaultIngredientTestPersistenceCheckerTestConfigurationTemplate {

}
