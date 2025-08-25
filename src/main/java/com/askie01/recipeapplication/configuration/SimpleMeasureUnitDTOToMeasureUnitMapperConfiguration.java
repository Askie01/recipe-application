package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.measureUnitDTO-to-measureUnit-type", havingValue = "simple")
public class SimpleMeasureUnitDTOToMeasureUnitMapperConfiguration {

    @Bean
    public MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper(LongIdMapper longIdMapper,
                                                                               StringNameMapper stringNameMapper,
                                                                               LongVersionMapper longVersionMapper) {
        return new SimpleMeasureUnitDTOToMeasureUnitMapper(longIdMapper, stringNameMapper, longVersionMapper);
    }
}
