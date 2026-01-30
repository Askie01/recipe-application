package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeDTOToRecipeMapperConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.mapper.recipeDTO-to-recipe-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public RecipeDTOToRecipeMapper simpleRecipeDTOToRecipeMapper(LongIdMapper longIdMapper,
                                                                 ImageMapper imageMapper,
                                                                 StringNameMapper stringNameMapper,
                                                                 DescriptionMapper descriptionMapper,
                                                                 DifficultyDTOToDifficultyMapper difficultyDTOToDifficultyMapper,
                                                                 CategoryDTOToCategoryMapper categoryDTOToCategoryMapper,
                                                                 IngredientDTOToIngredientMapper ingredientDTOToIngredientMapper,
                                                                 ServingsMapper servingsMapper,
                                                                 CookingTimeMapper cookingTimeMapper,
                                                                 InstructionsMapper instructionsMapper,
                                                                 LongVersionMapper longVersionMapper) {
        return new SimpleRecipeDTOToRecipeMapper(
                longIdMapper,
                imageMapper,
                stringNameMapper,
                descriptionMapper,
                difficultyDTOToDifficultyMapper,
                categoryDTOToCategoryMapper,
                ingredientDTOToIngredientMapper,
                servingsMapper,
                cookingTimeMapper,
                instructionsMapper,
                longVersionMapper
        );
    }
}
