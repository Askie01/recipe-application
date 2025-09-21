package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.InstructionsTestComparator;
import com.askie01.recipeapplication.comparator.InstructionsValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class InstructionsValueTestComparatorTestConfiguration {

    @Bean
    public InstructionsTestComparator instructionsTestComparator() {
        return new InstructionsValueTestComparator();
    }
}
