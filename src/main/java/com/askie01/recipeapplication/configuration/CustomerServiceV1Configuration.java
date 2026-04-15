package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CreateCustomerRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.UpdateCustomerDetailsRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.service.CustomerServiceV1;
import com.askie01.recipeapplication.service.DefaultCustomerServiceV1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomerServiceV1Configuration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.service.customer-v1",
            havingValue = "default",
            matchIfMissing = true
    )
    public CustomerServiceV1 defaultCustomerServiceV1(CustomerRepositoryV1 repository,
                                                      PasswordEncoder passwordEncoder,
                                                      CreateCustomerRequestBodyToCustomerMapper createRequestBodyMapper,
                                                      UpdateCustomerDetailsRequestBodyToCustomerMapper updateRequestBodyMapper) {
        return new DefaultCustomerServiceV1(
                repository,
                passwordEncoder,
                createRequestBodyMapper,
                updateRequestBodyMapper
        );
    }
}
