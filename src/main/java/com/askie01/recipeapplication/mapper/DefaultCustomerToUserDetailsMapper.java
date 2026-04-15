package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.entity.Customer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class DefaultCustomerToUserDetailsMapper implements CustomerToUserDetailsMapper {

    @Override
    public UserDetails mapToDTO(Customer customer) {
        final String username = customer.getUsername();
        final String password = customer.getPassword();
        return User.withUsername(username)
                .password(password)
                .build();
    }
}
