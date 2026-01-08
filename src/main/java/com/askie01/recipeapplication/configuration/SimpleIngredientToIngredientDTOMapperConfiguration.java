package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.ingredient-to-ingredientDTO-type", havingValue = "simple")
public class SimpleIngredientToIngredientDTOMapperConfiguration {

    @Bean
    public IngredientToIngredientDTOMapper ingredientToIngredientDTOMapper(LongIdMapper longIdMapper,
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
