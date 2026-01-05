package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedDescriptionMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = NonBlankDescriptionValidatorConfiguration.class)
public class ValidatedDescriptionMapperDefaultTestConfiguration
        extends ValidatedDescriptionMapperTestConfigurationTemplate {
}
