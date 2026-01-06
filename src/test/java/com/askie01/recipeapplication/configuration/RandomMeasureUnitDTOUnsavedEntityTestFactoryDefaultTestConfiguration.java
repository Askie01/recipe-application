package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomMeasureUnitDTOUnsavedEntityTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = FakerTestConfiguration.class)
public class RandomMeasureUnitDTOUnsavedEntityTestFactoryDefaultTestConfiguration
        extends RandomMeasureUnitDTOUnsavedEntityTestFactoryTestConfigurationTemplate {

}
