package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.CookingTimeTestPresenceChecker;
import com.askie01.recipeapplication.checker.DefaultCookingTimeTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultCookingTimeTestPresenceCheckerTestConfiguration {

    @Bean
    public CookingTimeTestPresenceChecker cookingTimeTestPresenceChecker() {
        return new DefaultCookingTimeTestPresenceChecker();
    }
}
