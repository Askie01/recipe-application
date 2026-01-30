package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryToCategoryDTOMapperConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.mapper.category-to-categoryDTO-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public CategoryToCategoryDTOMapper simpleCategoryToCategoryDTOMapper(LongIdMapper longIdMapper,
                                                                         StringNameMapper stringNameMapper,
                                                                         LongVersionMapper longVersionMapper) {
        return new SimpleCategoryToCategoryDTOMapper(
                longIdMapper,
                stringNameMapper,
                longVersionMapper
        );
    }
}
