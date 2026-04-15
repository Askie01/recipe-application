package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CreateCustomerRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.DefaultCreateCustomerRequestBodyToCustomerMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CreateCustomerRequestBodyToCustomerMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.create-customer-request-body-to-customer",
            havingValue = "default",
            matchIfMissing = true
    )
    public CreateCustomerRequestBodyToCustomerMapper defaultCreateCustomerRequestBodyToCustomerMapper() {
        return new DefaultCreateCustomerRequestBodyToCustomerMapper();
    }
}
