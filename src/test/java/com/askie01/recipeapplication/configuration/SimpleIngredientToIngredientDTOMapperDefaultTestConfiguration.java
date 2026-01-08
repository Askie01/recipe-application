package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.SimpleIngredientToIngredientDTOMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        SimpleLongIdMapperConfiguration.class,
        ValidatedStringNameMapperDefaultTestConfiguration.class,
        ValidatedAmountMapperDefaultTestConfiguration.class,
        SimpleMeasureUnitToMeasureUnitDTOMapperDefaultTestConfiguration.class,
        SimpleLongVersionMapperConfiguration.class
})
public class SimpleIngredientToIngredientDTOMapperDefaultTestConfiguration
        extends SimpleIngredientToIngredientDTOMapperTestConfigurationTemplate {
}
