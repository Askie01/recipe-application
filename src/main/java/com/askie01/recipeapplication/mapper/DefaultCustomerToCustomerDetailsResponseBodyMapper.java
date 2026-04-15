package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerDetailsResponseBody;
import com.askie01.recipeapplication.model.entity.Customer;

public class DefaultCustomerToCustomerDetailsResponseBodyMapper implements CustomerToCustomerDetailsResponseBodyMapper {

    @Override
    public CustomerDetailsResponseBody mapToDTO(Customer customer) {
        final CustomerDetailsResponseBody customerDetailsResponseBody = new CustomerDetailsResponseBody();
        map(customer, customerDetailsResponseBody);
        return customerDetailsResponseBody;
    }

    @Override
    public void map(Customer customer, CustomerDetailsResponseBody responseBody) {
        mapFirstName(customer, responseBody);
        mapLastName(customer, responseBody);
        mapEmail(customer, responseBody);
        mapMobileNumber(customer, responseBody);
    }

    private void mapFirstName(Customer customer, CustomerDetailsResponseBody responseBody) {
        final String firstName = customer.getFirstName();
        responseBody.setFirstName(firstName);
    }

    private void mapLastName(Customer customer, CustomerDetailsResponseBody responseBody) {
        final String lastName = customer.getLastName();
        responseBody.setLastName(lastName);
    }

    private void mapEmail(Customer customer, CustomerDetailsResponseBody responseBody) {
        final String email = customer.getEmail();
        responseBody.setEmail(email);
    }

    private void mapMobileNumber(Customer customer, CustomerDetailsResponseBody responseBody) {
        final String mobileNumber = customer.getMobileNumber();
        responseBody.setMobileNumber(mobileNumber);
    }
}
