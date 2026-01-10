package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.categoryDTO-to-category-type", havingValue = "simple")
public class SimpleCategoryDTOToCategoryMapperConfiguration {

    @Bean
    public CategoryDTOToCategoryMapper categoryDTOToCategoryMapper(LongIdMapper longIdMapper,
                                                                   StringNameMapper stringNameMapper,
                                                                   LongVersionMapper longVersionMapper) {
        return new SimpleCategoryDTOToCategoryMapper(longIdMapper, stringNameMapper, longVersionMapper);
    }
}
