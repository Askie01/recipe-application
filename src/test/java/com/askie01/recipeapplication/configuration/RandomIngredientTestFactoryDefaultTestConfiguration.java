package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomIngredientTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        FakerTestConfiguration.class,
        RandomMeasureUnitTestFactoryDefaultTestConfiguration.class
})
public class RandomIngredientTestFactoryDefaultTestConfiguration
        extends RandomIngredientTestFactoryTestConfigurationTemplate {

}
