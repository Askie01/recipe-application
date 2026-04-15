package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerPasswordRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerServiceV1 {
    void createCustomer(CreateCustomerRequestBody requestBody);

    Customer getCustomer(String username);

    Page<Customer> findCustomer(String username, Pageable pageable);

    void updateCustomerDetails(String username, UpdateCustomerDetailsRequestBody requestBody);

    void updateCustomerPassword(String username, UpdateCustomerPasswordRequestBody requestBody);

    void updateCustomerImage(String username, MultipartFile image);

    void restoreDefaultImage(String username);

    void deleteCustomer(String username);
}
