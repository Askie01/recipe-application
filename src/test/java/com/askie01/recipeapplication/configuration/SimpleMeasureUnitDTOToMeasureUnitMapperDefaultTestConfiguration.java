package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.SimpleMeasureUnitDTOToMeasureUnitMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        SimpleLongIdMapperConfiguration.class,
        ValidatedStringNameMapperDefaultTestConfiguration.class,
        SimpleLongVersionMapperConfiguration.class
})
public class SimpleMeasureUnitDTOToMeasureUnitMapperDefaultTestConfiguration
        extends SimpleMeasureUnitDTOToMeasureUnitMapperTestConfigurationTemplate {
}
