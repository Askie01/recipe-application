package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.SimpleMeasureUnitToMeasureUnitDTOMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        SimpleLongIdMapperConfiguration.class,
        ValidatedStringNameMapperDefaultTestConfiguration.class,
        SimpleLongVersionMapperConfiguration.class
})
public class SimpleMeasureUnitToMeasureUnitDTOMapperDefaultTestConfiguration
        extends SimpleMeasureUnitToMeasureUnitDTOMapperTestConfigurationTemplate {
}
