package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.ingredientDTO-to-ingredient-type", havingValue = "simple")
public class SimpleIngredientDTOToIngredientMapperConfiguration {

    @Bean
    public IngredientDTOToIngredientMapper ingredientDTOToIngredientMapper(LongIdMapper longIdMapper,
                                                                           StringNameMapper stringNameMapper,
                                                                           AmountMapper amountMapper,
                                                                           MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper,
                                                                           LongVersionMapper longVersionMapper) {
        return new SimpleIngredientDTOToIngredientMapper(
                longIdMapper,
                stringNameMapper,
                amountMapper,
                measureUnitDTOToMeasureUnitMapper,
                longVersionMapper);
    }
}
