package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultUserToUserDetailsMapper;
import com.askie01.recipeapplication.mapper.UserToUserDetailsMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserToUserDetailsMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.user-to-user-details",
            havingValue = "default",
            matchIfMissing = true
    )
    public UserToUserDetailsMapper defaultUserToUserDetailsMapper() {
        return new DefaultUserToUserDetailsMapper();
    }
}
