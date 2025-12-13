package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.DefaultCategoryTestPersistenceCheckerTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        DefaultLongIdTestPresenceCheckerTestConfiguration.class,
        DefaultStringNameTestPresenceCheckerTestConfiguration.class,
        DefaultSimpleAuditTestPresenceCheckerTestConfiguration.class,
        DefaultLongVersionTestPresenceCheckerTestConfiguration.class
})
public class DefaultCategoryTestPersistenceCheckerDefaultTestConfiguration
        extends DefaultCategoryTestPersistenceCheckerTestConfigurationTemplate {

}
