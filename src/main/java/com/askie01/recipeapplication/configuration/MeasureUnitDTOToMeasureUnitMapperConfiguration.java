package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MeasureUnitDTOToMeasureUnitMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.measureUnitDTO-to-measureUnit-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public MeasureUnitDTOToMeasureUnitMapper simpleMeasureUnitDTOToMeasureUnitMapper(LongIdMapper longIdMapper,
                                                                                     StringNameMapper stringNameMapper,
                                                                                     LongVersionMapper longVersionMapper) {
        return new SimpleMeasureUnitDTOToMeasureUnitMapper(
                longIdMapper,
                stringNameMapper,
                longVersionMapper
        );
    }
}
