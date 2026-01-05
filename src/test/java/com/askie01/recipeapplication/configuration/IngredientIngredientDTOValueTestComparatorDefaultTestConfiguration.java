package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.IngredientIngredientDTOValueTestComparatorTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorDefaultTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class
})
public class IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration
        extends IngredientIngredientDTOValueTestComparatorTestConfigurationTemplate {

}
