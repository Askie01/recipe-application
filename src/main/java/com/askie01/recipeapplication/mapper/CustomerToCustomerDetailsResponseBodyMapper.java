package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerDetailsResponseBody;
import com.askie01.recipeapplication.model.entity.Customer;

public interface CustomerToCustomerDetailsResponseBodyMapper
        extends Mapper<Customer, CustomerDetailsResponseBody>,
        ToDTOMapper<Customer, CustomerDetailsResponseBody> {
}
