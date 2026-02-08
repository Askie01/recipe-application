package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultUserToUserResponseBodyMapper;
import com.askie01.recipeapplication.mapper.UserToUserResponseBodyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UserToUserResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.user-to-user-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public UserToUserResponseBodyMapper defaultUserToUserResponseBodyMapper() {
        return new DefaultUserToUserResponseBodyMapper();
    }
}
