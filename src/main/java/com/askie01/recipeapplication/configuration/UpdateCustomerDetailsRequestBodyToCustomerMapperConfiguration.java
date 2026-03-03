package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultUpdateCustomerDetailsRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.UpdateCustomerDetailsRequestBodyToCustomerMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UpdateCustomerDetailsRequestBodyToCustomerMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.update-customer-details-request-body-to-customer",
            havingValue = "default",
            matchIfMissing = true
    )
    public UpdateCustomerDetailsRequestBodyToCustomerMapper defaultUpdateCustomerDetailsRequestBodyToCustomerMapper() {
        return new DefaultUpdateCustomerDetailsRequestBodyToCustomerMapper();
    }
}
