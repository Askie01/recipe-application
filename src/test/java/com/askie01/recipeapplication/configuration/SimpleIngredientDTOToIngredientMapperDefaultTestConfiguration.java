package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.SimpleIngredientDTOToIngredientMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        SimpleLongIdMapperConfiguration.class,
        ValidatedStringNameMapperDefaultTestConfiguration.class,
        ValidatedAmountMapperDefaultTestConfiguration.class,
        SimpleMeasureUnitDTOToMeasureUnitMapperDefaultTestConfiguration.class,
        SimpleLongVersionMapperConfiguration.class
})
public class SimpleIngredientDTOToIngredientMapperDefaultTestConfiguration
        extends SimpleIngredientDTOToIngredientMapperTestConfigurationTemplate {
}
