package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.measureUnit-to-measureUnitDTO-type", havingValue = "simple")
public class SimpleMeasureUnitToMeasureUnitDTOMapperConfiguration {

    @Bean
    public MeasureUnitToMeasureUnitDTOMapper measureUnitToMeasureUnitDTOMapper(LongIdMapper longIdMapper,
                                                                               StringNameMapper stringNameMapper,
                                                                               LongVersionMapper longVersionMapper) {
        return new SimpleMeasureUnitToMeasureUnitDTOMapper(longIdMapper, stringNameMapper, longVersionMapper);
    }
}
