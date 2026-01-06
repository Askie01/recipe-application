package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.AmountTestComparator;
import com.askie01.recipeapplication.comparator.AmountValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AmountValueTestComparatorTestConfiguration {

    @Bean
    public AmountTestComparator amountTestComparator() {
        return new AmountValueTestComparator();
    }
}
