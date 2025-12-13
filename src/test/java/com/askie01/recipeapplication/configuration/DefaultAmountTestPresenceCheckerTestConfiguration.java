package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.AmountTestPresenceChecker;
import com.askie01.recipeapplication.checker.DefaultAmountTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultAmountTestPresenceCheckerTestConfiguration {

    @Bean
    public AmountTestPresenceChecker amountTestPresenceChecker() {
        return new DefaultAmountTestPresenceChecker();
    }
}
