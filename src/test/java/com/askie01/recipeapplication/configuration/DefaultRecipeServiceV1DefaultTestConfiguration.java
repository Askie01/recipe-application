package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.DefaultRecipeServiceV1TestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = SimpleRecipeDTOToRecipeMapperDefaultTestConfiguration.class)
public class DefaultRecipeServiceV1DefaultTestConfiguration
        extends DefaultRecipeServiceV1TestConfigurationTemplate {
}
