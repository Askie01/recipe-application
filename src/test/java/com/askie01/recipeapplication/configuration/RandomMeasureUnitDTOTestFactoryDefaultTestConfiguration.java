package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomMeasureUnitDTOTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = FakerTestConfiguration.class)
public class RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration
        extends RandomMeasureUnitDTOTestFactoryTestConfigurationTemplate {

}
