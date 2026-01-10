package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedLongIdMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = PositiveLongIdValidatorConfiguration.class)
public class ValidatedLongIdMapperDefaultTestConfiguration
        extends ValidatedLongIdMapperTestConfigurationTemplate {
}
