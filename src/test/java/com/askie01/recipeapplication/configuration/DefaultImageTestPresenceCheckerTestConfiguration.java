package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultImageTestPresenceChecker;
import com.askie01.recipeapplication.checker.ImageTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultImageTestPresenceCheckerTestConfiguration {

    @Bean
    public ImageTestPresenceChecker imageTestPresenceChecker() {
        return new DefaultImageTestPresenceChecker();
    }
}
