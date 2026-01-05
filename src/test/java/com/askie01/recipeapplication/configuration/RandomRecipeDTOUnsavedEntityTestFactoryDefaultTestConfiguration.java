package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomRecipeDTOUnsavedEntityTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        FakerTestConfiguration.class,
        RandomDifficultyDTOTestFactoryDefaultTestConfiguration.class,
        RandomCategoryDTOUnsavedEntityTestFactoryDefaultTestConfiguration.class,
        RandomIngredientDTOUnsavedEntityTestFactoryDefaultTestConfiguration.class
})
public class RandomRecipeDTOUnsavedEntityTestFactoryDefaultTestConfiguration
        extends RandomRecipeDTOUnsavedEntityTestFactoryTestConfigurationTemplate {

}
