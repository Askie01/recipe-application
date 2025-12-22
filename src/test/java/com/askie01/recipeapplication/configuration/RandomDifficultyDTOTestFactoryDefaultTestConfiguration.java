package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomDifficultyDTOTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = FakerTestConfiguration.class)
public class RandomDifficultyDTOTestFactoryDefaultTestConfiguration
        extends RandomDifficultyDTOTestFactoryTestConfigurationTemplate {

}
