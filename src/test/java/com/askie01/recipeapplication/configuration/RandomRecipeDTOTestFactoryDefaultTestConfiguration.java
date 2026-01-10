package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomRecipeDTOTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        FakerTestConfiguration.class,
        RandomDifficultyDTOTestFactoryDefaultTestConfiguration.class,
        RandomCategoryDTOTestFactoryDefaultTestConfiguration.class,
        RandomIngredientDTOTestFactoryDefaultTestConfiguration.class
})
public class RandomRecipeDTOTestFactoryDefaultTestConfiguration
        extends RandomRecipeDTOTestFactoryTestConfigurationTemplate {

}
