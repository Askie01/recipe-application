package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.SimpleRecipeDTOToRecipeMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        SimpleLongIdMapperConfiguration.class,
        ValidatedImageMapperDefaultTestConfiguration.class,
        ValidatedStringNameMapperDefaultTestConfiguration.class,
        ValidatedDescriptionMapperDefaultTestConfiguration.class,
        SimpleDifficultyDTOToDifficultyMapperConfiguration.class,
        SimpleCategoryDTOToCategoryMapperDefaultTestConfiguration.class,
        SimpleIngredientDTOToIngredientMapperDefaultTestConfiguration.class,
        ValidatedServingsMapperDefaultTestConfiguration.class,
        ValidatedCookingTimeMapperDefaultTestConfiguration.class,
        ValidatedInstructionsMapperDefaultTestConfiguration.class,
        SimpleLongVersionMapperConfiguration.class
})
public class SimpleRecipeDTOToRecipeMapperDefaultTestConfiguration
        extends SimpleRecipeDTOToRecipeMapperTestConfigurationTemplate {
}
