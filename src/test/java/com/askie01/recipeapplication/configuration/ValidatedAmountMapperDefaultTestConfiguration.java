package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedAmountMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = PositiveAmountValidatorConfiguration.class)
public class ValidatedAmountMapperDefaultTestConfiguration
        extends ValidatedAmountMapperTestConfigurationTemplate {
}
