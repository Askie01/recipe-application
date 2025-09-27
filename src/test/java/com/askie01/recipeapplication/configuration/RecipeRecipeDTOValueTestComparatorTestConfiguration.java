package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RecipeRecipeDTOValueTestComparatorTestConfiguration {

    @Bean
    public RecipeRecipeDTOTestComparator recipeRecipeDTOTestComparator(LongIdTestComparator longIdTestComparator,
                                                                       ImageTestComparator imageTestComparator,
                                                                       StringNameTestComparator stringNameTestComparator,
                                                                       DescriptionTestComparator descriptionTestComparator,
                                                                       DifficultyDifficultyDTOTestComparator difficultyDifficultyDTOTestComparator,
                                                                       CategoryCategoryDTOTestComparator categoryCategoryDTOTestComparator,
                                                                       IngredientIngredientDTOTestComparator ingredientIngredientDTOTestComparator,
                                                                       ServingsTestComparator servingsTestComparator,
                                                                       CookingTimeTestComparator cookingTimeTestComparator,
                                                                       InstructionsTestComparator instructionsTestComparator,
                                                                       LongVersionTestComparator longVersionTestComparator) {
        return new RecipeRecipeDTOValueTestComparator(
                longIdTestComparator,
                imageTestComparator,
                stringNameTestComparator,
                descriptionTestComparator,
                difficultyDifficultyDTOTestComparator,
                categoryCategoryDTOTestComparator,
                ingredientIngredientDTOTestComparator,
                servingsTestComparator,
                cookingTimeTestComparator,
                instructionsTestComparator,
                longVersionTestComparator
        );
    }
}
