package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CustomerToUserDetailsMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerToUserDetailsMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CustomerToUserDetailsMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.customer-to-user-details",
            havingValue = "default",
            matchIfMissing = true
    )
    public CustomerToUserDetailsMapper defaultCustomerToUserDetailsMapper() {
        return new DefaultCustomerToUserDetailsMapper();
    }
}
