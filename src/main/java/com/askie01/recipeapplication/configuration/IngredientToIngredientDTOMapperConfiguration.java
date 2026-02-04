package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class IngredientToIngredientDTOMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.ingredient-to-ingredientDTO-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public IngredientToIngredientDTOMapper simpleIngredientToIngredientDTOMapper(LongIdMapper longIdMapper,
                                                                                 StringNameMapper stringNameMapper,
                                                                                 AmountMapper amountMapper,
                                                                                 MeasureUnitToMeasureUnitDTOMapper measureUnitToMeasureUnitDTOMapper,
                                                                                 LongVersionMapper longVersionMapper) {
        return new SimpleIngredientToIngredientDTOMapper(
                longIdMapper,
                stringNameMapper,
                amountMapper,
                measureUnitToMeasureUnitDTOMapper,
                longVersionMapper
        );
    }
}
