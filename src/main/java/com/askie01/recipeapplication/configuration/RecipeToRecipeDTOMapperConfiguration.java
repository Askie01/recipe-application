package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeToRecipeDTOMapperConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.mapper.recipe-to-recipeDTO-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public RecipeToRecipeDTOMapper simpleRecipeToRecipeDTOMapper(LongIdMapper longIdMapper,
                                                                 ImageMapper imageMapper,
                                                                 StringNameMapper stringNameMapper,
                                                                 DescriptionMapper descriptionMapper,
                                                                 DifficultyToDifficultyDTOMapper difficultyToDifficultyDTOMapper,
                                                                 CategoryToCategoryDTOMapper categoryToCategoryDTOMapper,
                                                                 IngredientToIngredientDTOMapper ingredientToIngredientDTOMapper,
                                                                 ServingsMapper servingsMapper,
                                                                 CookingTimeMapper cookingTimeMapper,
                                                                 InstructionsMapper instructionsMapper,
                                                                 LongVersionMapper longVersionMapper) {
        return new SimpleRecipeToRecipeDTOMapper(
                longIdMapper,
                imageMapper,
                stringNameMapper,
                descriptionMapper,
                difficultyToDifficultyDTOMapper,
                categoryToCategoryDTOMapper,
                ingredientToIngredientDTOMapper,
                servingsMapper,
                cookingTimeMapper,
                instructionsMapper,
                longVersionMapper
        );
    }
}
