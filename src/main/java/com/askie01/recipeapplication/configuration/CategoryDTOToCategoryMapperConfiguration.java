package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CategoryDTOToCategoryMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.categoryDTO-to-category-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public CategoryDTOToCategoryMapper simpleCategoryDTOToCategoryMapper(LongIdMapper longIdMapper,
                                                                         StringNameMapper stringNameMapper,
                                                                         LongVersionMapper longVersionMapper) {
        return new SimpleCategoryDTOToCategoryMapper(
                longIdMapper,
                stringNameMapper,
                longVersionMapper
        );
    }
}
