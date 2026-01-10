package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.category-to-categoryDTO-type", havingValue = "simple")
public class SimpleCategoryToCategoryDTOMapperConfiguration {

    @Bean
    public CategoryToCategoryDTOMapper categoryToCategoryDTOMapper(LongIdMapper longIdMapper,
                                                                   StringNameMapper stringNameMapper,
                                                                   LongVersionMapper longVersionMapper) {
        return new SimpleCategoryToCategoryDTOMapper(longIdMapper, stringNameMapper, longVersionMapper);
    }
}
