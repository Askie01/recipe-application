package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.DefaultRecipeTestPersistenceCheckerTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        DefaultLongIdTestPresenceCheckerTestConfiguration.class,
        DefaultImageTestPresenceCheckerTestConfiguration.class,
        DefaultStringNameTestPresenceCheckerTestConfiguration.class,
        DefaultDescriptionTestPresenceCheckerTestConfiguration.class,
        DefaultRecipeDifficultyTestPresenceCheckerTestConfiguration.class,
        DefaultCategoryTestPersistenceCheckerDefaultTestConfiguration.class,
        DefaultIngredientTestPersistenceCheckerDefaultTestConfiguration.class,
        DefaultServingsTestPresenceCheckerTestConfiguration.class,
        DefaultCookingTimeTestPresenceCheckerTestConfiguration.class,
        DefaultInstructionsTestPresenceCheckerTestConfiguration.class,
        DefaultSimpleAuditTestPresenceCheckerTestConfiguration.class,
        DefaultLongVersionTestPresenceCheckerTestConfiguration.class
})
public class DefaultRecipeTestPersistenceCheckerDefaultTestConfiguration
        extends DefaultRecipeTestPersistenceCheckerTestConfigurationTemplate {

}
