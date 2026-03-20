package com.askie01.recipeapplication.api.v1;

import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.mapper.CustomerToCustomerDetailsResponseBodyMapper;
import com.askie01.recipeapplication.mapper.CustomerToCustomerProfileResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.service.CustomerServiceV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.customer.v1.enabled", havingValue = "true", matchIfMissing = true)
public class CustomerRestControllerV1 {

    private final CustomerServiceV1 service;
    private final CustomerToCustomerProfileResponseBodyMapper customerToCustomerProfileMapper;
    private final CustomerToCustomerDetailsResponseBodyMapper customerToCustomerDetailsMapper;

    @PostMapping("/register")
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CreateCustomerRequestBody requestBody) {
        service.createCustomer(requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/details")
    public ResponseEntity<CustomerDetailsResponseBody> getCustomerDetails(Authentication authentication) {
        System.out.println("Message from controller");
        System.out.println("Authentication: " + authentication);
        final String username = authentication.getName();
        System.out.println("Got: " + username);
        final Customer customer = service.getCustomer(username);
        final CustomerDetailsResponseBody responseBody = customerToCustomerDetailsMapper.mapToDTO(customer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getCustomerImage(@PathVariable String username) {
        final byte[] image = service.getCustomer(username).getImage();
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<CustomerProfileResponseBody>> searchCustomers(@RequestParam(required = false) String query,
                                                                                     @PageableDefault(
                                                                                             sort = "username",
                                                                                             direction = Sort.Direction.ASC
                                                                                     ) Pageable pageable) {
        final Page<CustomerProfileResponseBody> customerProfiles = service
                .findCustomer(query, pageable)
                .map(customerToCustomerProfileMapper::mapToDTO);
        final PageResponse<CustomerProfileResponseBody> responseBody = PageResponse.from(customerProfiles);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<CustomerProfileResponseBody> getCustomerProfile(@PathVariable String username) {
        final Customer customer = service.getCustomer(username);
        final CustomerProfileResponseBody responseBody = customerToCustomerProfileMapper.mapToDTO(customer);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/details")
    public ResponseEntity<Void> updateCustomerDetails(Authentication authentication,
                                                      @Valid @RequestBody UpdateCustomerDetailsRequestBody requestBody) {
        final String username = authentication.getName();
        service.updateCustomerDetails(username, requestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updateCustomerPassword(Authentication authentication,
                                                       @Valid @RequestBody UpdateCustomerPasswordRequestBody requestBody) {
        final String username = authentication.getName();
        service.updateCustomerPassword(username, requestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCustomerImage(Authentication authentication,
                                                    @RequestPart MultipartFile image) {
        final String username = authentication.getName();
        service.updateCustomerImage(username, image);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/image/restore")
    public ResponseEntity<Void> restoreDefaultImage(Authentication authentication) {
        final String username = authentication.getName();
        service.restoreDefaultImage(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(Authentication authentication) {
        final String username = authentication.getName();
        service.deleteCustomer(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
