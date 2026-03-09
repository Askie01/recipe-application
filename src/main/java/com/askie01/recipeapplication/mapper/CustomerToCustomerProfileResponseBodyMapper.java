package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerProfileResponseBody;
import com.askie01.recipeapplication.model.entity.Customer;

public interface CustomerToCustomerProfileResponseBodyMapper
        extends Mapper<Customer, CustomerProfileResponseBody>,
        ToDTOMapper<Customer, CustomerProfileResponseBody> {
}
