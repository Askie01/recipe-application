package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedServingsMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = PositiveServingsValidatorConfiguration.class)
public class ValidatedServingsMapperDefaultTestConfiguration
        extends ValidatedServingsMapperTestConfigurationTemplate {
}
