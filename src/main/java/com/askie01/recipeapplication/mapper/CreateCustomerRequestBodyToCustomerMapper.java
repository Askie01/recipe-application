package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;

public interface CreateCustomerRequestBodyToCustomerMapper
        extends Mapper<CreateCustomerRequestBody, Customer>,
        ToEntityMapper<CreateCustomerRequestBody, Customer> {
}
