package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedStringNameMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = NonBlankStringNameValidatorConfiguration.class)
public class ValidatedStringNameMapperDefaultTestConfiguration
        extends ValidatedStringNameMapperTestConfigurationTemplate {
}
