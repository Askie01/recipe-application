package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomIngredientDTOUnsavedEntityTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        FakerTestConfiguration.class,
        RandomMeasureUnitDTOUnsavedEntityTestFactoryDefaultTestConfiguration.class
})
public class RandomIngredientDTOUnsavedEntityTestFactoryDefaultTestConfiguration
        extends RandomIngredientDTOUnsavedEntityTestFactoryTestConfigurationTemplate {

}
