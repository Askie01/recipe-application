package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.ServingsTestComparator;
import com.askie01.recipeapplication.comparator.ServingsValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServingsValueTestComparatorTestConfiguration {

    @Bean
    public ServingsTestComparator servingsTestComparator() {
        return new ServingsValueTestComparator();
    }
}
