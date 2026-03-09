package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.entity.Customer;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerToUserDetailsMapper
        extends ToDTOMapper<Customer, UserDetails> {
}
