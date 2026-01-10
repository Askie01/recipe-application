package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultLongVersionTestPresenceChecker;
import com.askie01.recipeapplication.checker.LongVersionTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultLongVersionTestPresenceCheckerTestConfiguration {

    @Bean
    public LongVersionTestPresenceChecker longVersionTestPresenceChecker() {
        return new DefaultLongVersionTestPresenceChecker();
    }
}
