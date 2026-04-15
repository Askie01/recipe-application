package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;

import java.util.HashSet;

public class DefaultUpdateCustomerDetailsRequestBodyToCustomerMapper implements UpdateCustomerDetailsRequestBodyToCustomerMapper {

    @Override
    public Customer mapToEntity(UpdateCustomerDetailsRequestBody requestBody) {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        map(requestBody, customer);
        return customer;
    }

    @Override
    public void map(UpdateCustomerDetailsRequestBody requestBody, Customer customer) {
        mapFirstName(requestBody, customer);
        mapLastName(requestBody, customer);
        mapEmail(requestBody, customer);
        mapMobileNumber(requestBody, customer);
    }

    private void mapFirstName(UpdateCustomerDetailsRequestBody requestBody, Customer customer) {
        final String firstName = requestBody.getFirstName();
        customer.setFirstName(firstName);
    }

    private void mapLastName(UpdateCustomerDetailsRequestBody requestBody, Customer customer) {
        final String lastName = requestBody.getLastName();
        customer.setLastName(lastName);
    }

    private void mapEmail(UpdateCustomerDetailsRequestBody requestBody, Customer customer) {
        final String email = requestBody.getEmail();
        customer.setEmail(email);
    }

    private void mapMobileNumber(UpdateCustomerDetailsRequestBody requestBody, Customer customer) {
        final String mobileNumber = requestBody.getMobileNumber();
        customer.setMobileNumber(mobileNumber);
    }
}
