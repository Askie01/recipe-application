package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomMeasureUnitTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = FakerTestConfiguration.class)
public class RandomMeasureUnitTestFactoryDefaultTestConfiguration
        extends RandomMeasureUnitTestFactoryTestConfigurationTemplate {

}
