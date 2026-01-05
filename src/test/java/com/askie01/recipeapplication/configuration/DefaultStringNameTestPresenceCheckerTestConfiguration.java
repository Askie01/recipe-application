package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultStringNameTestPresenceChecker;
import com.askie01.recipeapplication.checker.StringNameTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultStringNameTestPresenceCheckerTestConfiguration {

    @Bean
    public StringNameTestPresenceChecker stringNameTestPresenceChecker() {
        return new DefaultStringNameTestPresenceChecker();
    }
}
