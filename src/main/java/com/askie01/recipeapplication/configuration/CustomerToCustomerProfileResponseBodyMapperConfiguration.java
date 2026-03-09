package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CustomerToCustomerProfileResponseBodyMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerToCustomerProfileResponseBodyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CustomerToCustomerProfileResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.customer-to-customer-profile-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public CustomerToCustomerProfileResponseBodyMapper defaultCustomerToCustomerProfileResponseBodyMapper() {
        return new DefaultCustomerToCustomerProfileResponseBodyMapper();
    }
}
