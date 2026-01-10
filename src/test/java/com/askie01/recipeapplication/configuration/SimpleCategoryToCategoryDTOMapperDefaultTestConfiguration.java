package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.configuration.template.SimpleCategoryToCategoryDTOMapperTestConfigurationTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        SimpleLongIdMapperConfiguration.class,
        ValidatedStringNameMapperDefaultTestConfiguration.class,
        SimpleLongVersionMapperConfiguration.class
})
public class SimpleCategoryToCategoryDTOMapperDefaultTestConfiguration
        extends SimpleCategoryToCategoryDTOMapperTestConfigurationTemplate {
}
