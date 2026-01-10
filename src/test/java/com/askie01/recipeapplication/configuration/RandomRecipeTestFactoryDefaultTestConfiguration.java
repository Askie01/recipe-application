package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RandomRecipeTestFactoryTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        FakerTestConfiguration.class,
        RandomDifficultyTestFactoryDefaultTestConfiguration.class,
        RandomCategoryTestFactoryDefaultTestConfiguration.class,
        RandomIngredientTestFactoryDefaultTestConfiguration.class
})
public class RandomRecipeTestFactoryDefaultTestConfiguration
        extends RandomRecipeTestFactoryTestConfigurationTemplate {

}
