package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.comparator.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IngredientIngredientDTOValueTestComparatorTestConfigurationTemplate {

    @Bean
    public IngredientIngredientDTOTestComparator ingredientIngredientDTOTestComparator(LongIdTestComparator longIdTestComparator,
                                                                                       StringNameTestComparator stringNameTestComparator,
                                                                                       AmountTestComparator amountTestComparator,
                                                                                       MeasureUnitMeasureUnitDTOTestComparator measureUnitMeasureUnitDTOTestComparator,
                                                                                       LongVersionTestComparator longVersionTestComparator) {
        return new IngredientIngredientDTOValueTestComparator(
                longIdTestComparator,
                stringNameTestComparator,
                amountTestComparator,
                measureUnitMeasureUnitDTOTestComparator,
                longVersionTestComparator);
    }
}
