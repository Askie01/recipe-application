package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;

public interface UpdateCustomerDetailsRequestBodyToCustomerMapper
        extends Mapper<UpdateCustomerDetailsRequestBody, Customer>,
        ToEntityMapper<UpdateCustomerDetailsRequestBody, Customer> {
}
