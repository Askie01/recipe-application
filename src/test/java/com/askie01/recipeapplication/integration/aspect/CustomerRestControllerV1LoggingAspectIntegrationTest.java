package com.askie01.recipeapplication.integration.aspect;

import com.askie01.recipeapplication.aspect.CustomerRestControllerV1LoggingAspect;
import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerPasswordRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.service.CustomerServiceV1;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureDataJpa
@AutoConfigureRestTestClient
@TestPropertySource(properties = "api.customer.v1.enabled=true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("UserRestControllerV1LoggingAspect integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class CustomerRestControllerV1LoggingAspectIntegrationTest {

    @MockitoSpyBean
    private final CustomerRestControllerV1LoggingAspect aspect;

    @MockitoBean
    private final CustomerServiceV1 service;
    private final RestTestClient restTestClient;
    private final CustomerRepositoryV1 repository;

    private CreateCustomerRequestBody createCustomerRequestBody;
    private UpdateCustomerDetailsRequestBody updateCustomerDetailsRequestBody;
    private UpdateCustomerPasswordRequestBody updateCustomerPasswordRequestBody;

    @BeforeEach
    void setUp() {
        this.createCustomerRequestBody = getTestCreateCustomerRequestBody();
        this.updateCustomerDetailsRequestBody = getTestUpdateCustomerDetailsRequestBody();
        this.updateCustomerPasswordRequestBody = getTestUpdateCustomerPasswordRequestBody();
    }

    private CreateCustomerRequestBody getTestCreateCustomerRequestBody() {
        return CreateCustomerRequestBody.builder()
                .username("user")
                .password("{noop}user")
                .firstName("simple")
                .lastName("user")
                .email("simple.user@gmail.com")
                .mobileNumber("123456789")
                .build();
    }

    private UpdateCustomerDetailsRequestBody getTestUpdateCustomerDetailsRequestBody() {
        return UpdateCustomerDetailsRequestBody.builder()
                .firstName("Test first name")
                .lastName("Test last name")
                .email("test@example.com")
                .mobileNumber("1234567890")
                .build();
    }

    private UpdateCustomerPasswordRequestBody getTestUpdateCustomerPasswordRequestBody() {
        return UpdateCustomerPasswordRequestBody.builder()
                .oldPassword("{noop}askie01")
                .newPassword("{noop}askie02")
                .build();
    }

    @Test
    @DisplayName("aspect should log two statements when createCustomer() method is called with valid request body")
    void aspect_whenCreateCustomerIsCalledWithValidRequestBody_logsTwoStatements() {
        restTestClient.post().uri("/api/v1/customers/register")
                .body(createCustomerRequestBody)
                .exchange()
                .expectStatus()
                .isCreated();
        verify(aspect, times(1)).logBeforeCreateCustomer(any());
        verify(aspect, times(1)).logAfterCreateCustomer(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when createCustomer() method is called with invalid request body")
    void aspect_whenCreateCustomerIsCalledWithInvalidRequestBody_skipsLogStatements() {
        restTestClient.post().uri("/api/v1/customers/register")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when createCustomer() method throws an exception")
    void aspect_whenCreateCustomerThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .createCustomer(any(CreateCustomerRequestBody.class));
        restTestClient.post().uri("/api/v1/customers/register")
                .body(createCustomerRequestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
        verify(aspect, times(1)).logBeforeCreateCustomer(any());
        verify(aspect, times(1)).logAndRethrowCreateCustomer(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getCustomerDetails() method is called with authentication")
    void aspect_whenGetCustomerDetailsIsCalledWithAuthentication_logsTwoStatements() {
        when(service.getCustomer(any(String.class)))
                .thenReturn(new Customer());
        restTestClient.get().uri("/api/v1/customers/details")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetCustomerDetails(any());
        verify(aspect, times(1)).logAfterGetCustomerDetails(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when getCustomerDetails() method is called without authentication")
    void aspect_whenGetCustomerDetailsIsCalledWithoutAuthentication_skipsLogStatements() {
        restTestClient.get().uri("/api/v1/customers/details")
                .exchange()
                .expectStatus()
                .isUnauthorized();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when getCustomerDetails() method throws an exception")
    void aspect_whenGetCustomerDetailsThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .getCustomer(any(String.class));
        restTestClient.get().uri("/api/v1/customers/details")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isBadRequest();
        verify(aspect, times(1)).logBeforeGetCustomerDetails(any());
        verify(aspect, times(1)).logAndRethrowGetCustomerDetails(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getCustomerImage() method is called")
    void aspect_whenGetCustomerImageIsCalled_logsTwoStatements() {
        when(service.getCustomer(any(String.class)))
                .thenReturn(new Customer());
        final String username = repository.findAll().stream().findAny().orElseThrow().getUsername();
        restTestClient.get().uri("/api/v1/customers/image/{username}", username)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetCustomerImage(any());
        verify(aspect, times(1)).logAfterGetCustomerImage(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when getCustomerImage() method is called without username")
    void aspect_whenGetCustomerImageIsCalledWithoutUsername_skipsLogStatements() {
        restTestClient.get().uri("/api/v1/customers/image")
                .exchange()
                .expectStatus()
                .is4xxClientError();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when getCustomerImage() method is called with invalid username")
    void aspect_whenGetCustomerImageIsCalledWithInvalidUsername_logsTwoStatements() throws Throwable {
        when(service.getCustomer(any(String.class)))
                .thenThrow(UsernameNotFoundException.class);
        restTestClient.get().uri("/api/v1/customers/image/invalid-username")
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeGetCustomerImage(any());
        verify(aspect, times(1)).logAndRethrowGetCustomerImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when searchCustomers() method is called with query")
    void aspect_whenSearchCustomersIsCalledWithQuery_logsTwoStatements() {
        when(service.findCustomer(any(String.class), any(Pageable.class)))
                .thenReturn(Page.empty());
        restTestClient.get().uri("/api/v1/customers/search?query=user")
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeSearchCustomers(any());
        verify(aspect, times(1)).logAfterSearchCustomers(any());
    }

    @Test
    @DisplayName("aspect should log two statements when searchCustomers() method is called without query")
    void aspect_whenSearchCustomersIsCalledWithoutQuery_logsTwoStatements() {
        when(service.findCustomer(any(String.class), any(Pageable.class)))
                .thenReturn(Page.empty());
        restTestClient.get().uri("/api/v1/customers/search")
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeSearchCustomers(any());
        verify(aspect, times(1)).logAfterSearchCustomers(any());
    }

    @Test
    @DisplayName("aspect should log two statements when searchCustomers() method throws an exception")
    void aspect_whenSearchCustomersThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .findCustomer(any(String.class), any(Pageable.class));
        restTestClient.get().uri("/api/v1/customers/search?query=invalid-query")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verify(aspect, times(1)).logBeforeSearchCustomers(any());
        verify(aspect, times(1)).logAndRethrowSearchCustomers(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getCustomerProfile() method is called with valid username")
    void aspect_whenGetCustomerProfileIsCalledWithValidUsername_logsTwoStatements() {
        when(service.getCustomer(any(String.class)))
                .thenReturn(new Customer());
        final String username = repository.findAll().stream().findAny().orElseThrow().getUsername();
        restTestClient.get().uri("/api/v1/customers/profile/{username}", username)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetCustomerProfile(any());
        verify(aspect, times(1)).logAfterGetCustomerProfile(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when getCustomerProfile() method is called without username")
    void aspect_whenGetCustomerProfileIsCalledWithInvalidUsername_logsTwoStatements() throws Throwable {
        when(service.getCustomer(any(String.class)))
                .thenThrow(UsernameNotFoundException.class);
        restTestClient.get().uri("/api/v1/customers/profile/invalid-username")
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeGetCustomerProfile(any());
        verify(aspect, times(1)).logAndRethrowGetCustomerProfile(any());
    }

    @Test
    @DisplayName("aspect should log two statements when updateCustomerDetails() method is called with authentication and valid request body")
    void aspect_whenUpdateCustomerDetailsIsCalledWithAuthenticationAndValidRequestBody_logsTwoStatements() {
        restTestClient.put().uri("/api/v1/customers/details")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(updateCustomerDetailsRequestBody)
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeUpdateCustomerDetails(any());
        verify(aspect, times(1)).logAfterUpdateCustomerDetails(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when updateCustomerDetails() method is called without authentication")
    void aspect_whenUpdateCustomerDetailsIsCalledWithoutAuthentication_skipsLogStatements() {
        restTestClient.put().uri("/api/v1/customers/details")
                .body(updateCustomerDetailsRequestBody)
                .exchange()
                .expectStatus()
                .isUnauthorized();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when updateCustomerDetails() method throws an exception")
    void aspect_whenUpdateCustomerDetailsThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .updateCustomerDetails(any(String.class), any(UpdateCustomerDetailsRequestBody.class));
        restTestClient.put().uri("/api/v1/customers/details")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(updateCustomerDetailsRequestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
        verify(aspect, times(1)).logBeforeUpdateCustomerDetails(any());
        verify(aspect, times(1)).logAndRethrowUpdateCustomerDetails(any());
    }

    @Test
    @DisplayName("aspect should log two statements when updateCustomerPassword() method is called with authentication and valid request body")
    void aspect_whenUpdateCustomerPasswordIsCalledWithAuthenticationAndValidRequestBody_logsTwoStatements() {
        updateCustomerPasswordRequestBody.setOldPassword("{noop}user");
        restTestClient.put().uri("/api/v1/customers/password")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(updateCustomerPasswordRequestBody)
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeUpdateCustomerPassword(any());
        verify(aspect, times(1)).logAfterUpdateCustomerPassword(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when updateCustomerPassword() method is called without authentication")
    void aspect_whenUpdateCustomerPasswordIsCalledWithoutAuthentication_skipsLogStatements() {
        updateCustomerPasswordRequestBody.setOldPassword("{noop}user");
        restTestClient.put().uri("/api/v1/customers/password")
                .body(updateCustomerPasswordRequestBody)
                .exchange()
                .expectStatus()
                .isUnauthorized();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when updateCustomerPassword() method throws an exception")
    void aspect_whenUpdateCustomerPasswordIsCalledWithInvalidRequestBody_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .updateCustomerPassword(
                        any(String.class),
                        any(UpdateCustomerPasswordRequestBody.class)
                );
        restTestClient.put().uri("/api/v1/customers/password")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(updateCustomerPasswordRequestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
        verify(aspect, times(1)).logBeforeUpdateCustomerPassword(any());
        verify(aspect, times(1)).logAndRethrowUpdateCustomerPassword(any());
    }

    @Test
    @DisplayName("aspect should log two statements when restoreDefaultImage() method is called with authentication")
    void aspect_whenRestoreDefaultImageIsCalledWithAuthentication_logsTwoStatements() {
        restTestClient.put().uri("/api/v1/customers/image/restore")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeRestoreDefaultImage(any());
        verify(aspect, times(1)).logAfterRestoreDefaultImage(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when restoreDefaultImage() method is called without authentication")
    void aspect_whenRestoreDefaultImageIsCalledWithoutAuthentication_skipsLogStatements() {
        restTestClient.put().uri("/api/v1/customers/image")
                .exchange()
                .expectStatus()
                .isUnauthorized();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when restoreDefaultImage() method throws an exception")
    void aspect_whenRestoreDefaultImageThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .restoreDefaultImage(any(String.class));
        restTestClient.put().uri("/api/v1/customers/image/restore")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isBadRequest();
        verify(aspect, times(1)).logBeforeRestoreDefaultImage(any());
        verify(aspect, times(1)).logAndRethrowRestoreDefaultImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when deleteCustomer() method is called with authentication")
    void aspect_whenDeleteCustomerIsCalledWithAuthentication_logsTwoStatements() {
        restTestClient.delete().uri("/api/v1/customers")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeDeleteCustomer(any());
        verify(aspect, times(1)).logAfterDeleteCustomer(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when deleteCustomer() method is called without authentication")
    void aspect_whenDeleteCustomerIsCalledWithoutAuthentication_skipsLogStatements() {
        restTestClient.delete().uri("/api/v1/customers")
                .exchange()
                .expectStatus()
                .isUnauthorized();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when deleteCustomer() method throws an exception")
    void aspect_whenDeleteCustomerThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(IllegalArgumentException.class)
                .when(service)
                .deleteCustomer(any(String.class));
        restTestClient.delete().uri("/api/v1/customers")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .is4xxClientError();
        verify(aspect, times(1)).logBeforeDeleteCustomer(any());
        verify(aspect, times(1)).logAndRethrowDeleteCustomer(any());
    }
}