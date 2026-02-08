package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.service.DefaultUserServiceV1;
import com.askie01.recipeapplication.service.UserServiceV1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserServiceV1Configuration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.service.user-v2",
            havingValue = "default",
            matchIfMissing = true
    )
    public UserServiceV1 defaultUserServiceV1() {
        return new DefaultUserServiceV1();
    }
}
