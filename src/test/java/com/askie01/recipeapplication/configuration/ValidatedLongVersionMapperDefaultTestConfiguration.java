package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedLongVersionMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = PositiveLongVersionValidatorConfiguration.class)
public class ValidatedLongVersionMapperDefaultTestConfiguration
        extends ValidatedLongVersionMapperTestConfigurationTemplate {
}
