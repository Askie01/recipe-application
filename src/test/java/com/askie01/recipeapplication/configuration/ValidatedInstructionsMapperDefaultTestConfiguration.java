package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedInstructionsMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = NonBlankInstructionsValidatorConfiguration.class)
public class ValidatedInstructionsMapperDefaultTestConfiguration
        extends ValidatedInstructionsMapperTestConfigurationTemplate {
}
