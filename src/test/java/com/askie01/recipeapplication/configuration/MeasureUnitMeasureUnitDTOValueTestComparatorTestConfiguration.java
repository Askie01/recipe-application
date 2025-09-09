package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MeasureUnitMeasureUnitDTOValueTestComparatorTestConfiguration {

    @Bean
    public MeasureUnitMeasureUnitDTOTestComparator measureUnitMeasureUnitDTOTestComparator(LongIdTestComparator longIdTestComparator,
                                                                                           StringNameTestComparator stringNameTestComparator,
                                                                                           LongVersionTestComparator longVersionTestComparator) {
        return new MeasureUnitMeasureUnitDTOValueTestComparator(
                longIdTestComparator,
                stringNameTestComparator,
                longVersionTestComparator);
    }
}
