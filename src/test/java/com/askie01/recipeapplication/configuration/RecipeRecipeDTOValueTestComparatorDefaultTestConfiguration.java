package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.RecipeRecipeDTOValueTestComparatorTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        LongIdValueTestComparatorTestConfiguration.class,
        ImageValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        DescriptionValueTestComparatorTestConfiguration.class,
        DifficultyDifficultyDTOValueTestComparatorTestConfiguration.class,
        CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration.class,
        IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration.class,
        ServingsValueTestComparatorTestConfiguration.class,
        CookingTimeValueTestComparatorTestConfiguration.class,
        InstructionsValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class
})
public class RecipeRecipeDTOValueTestComparatorDefaultTestConfiguration
        extends RecipeRecipeDTOValueTestComparatorTestConfigurationTemplate {
}
