package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedCookingTimeMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = PositiveCookingTimeValidatorConfiguration.class)
public class ValidatedCookingTimeMapperDefaultTestConfiguration
        extends ValidatedCookingTimeMapperTestConfigurationTemplate {
}
