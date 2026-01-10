package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.ValidatedImageMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = FiveMegaBytesImageValidatorConfiguration.class)
public class ValidatedImageMapperDefaultTestConfiguration
        extends ValidatedImageMapperTestConfigurationTemplate {
}
