package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CustomerToCustomerDetailsResponseBodyMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerToCustomerDetailsResponseBodyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CustomerToCustomerDetailsResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.customer-to-customer-details-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public CustomerToCustomerDetailsResponseBodyMapper defaultCustomerToCustomerDetailsResponseBodyMapper() {
        return new DefaultCustomerToCustomerDetailsResponseBodyMapper();
    }
}
