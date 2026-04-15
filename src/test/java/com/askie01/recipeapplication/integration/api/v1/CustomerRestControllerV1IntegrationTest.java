package com.askie01.recipeapplication.integration.api.v1;

import com.askie01.recipeapplication.api.v1.CustomerRestControllerV1;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(controllers = CustomerRestControllerV1.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureDataJpa
@AutoConfigureRestTestClient
@Import(value = {
        CustomerServiceV1Configuration.class,
        CustomerToCustomerProfileResponseBodyMapperConfiguration.class,
        CustomerToCustomerDetailsResponseBodyMapperConfiguration.class,
        CreateCustomerRequestBodyToCustomerMapperConfiguration.class,
        UserDetailsServiceConfiguration.class,
        UpdateCustomerDetailsRequestBodyToCustomerMapperConfiguration.class,
        CustomerToUserDetailsMapperConfiguration.class,
        SecurityFilterChainConfiguration.class,
        PasswordEncoderConfiguration.class,
        JpaAuditingConfiguration.class,
})
@TestPropertySource(properties = {
        "api.customer.v1.enabled=true",
        "component.mapper.customer-to-customer-profile-response-body=default",
        "component.mapper.customer-to-customer-details-response-body=default",
        "component.service.customer-v1=default",
        "component.mapper.create-customer-request-body-to-customer=default",
        "component.mapper.update-customer-details-request-body-to-customer=default",
        "component.mapper.customer-to-user-details=default",
        "component.service.user-details=default",
        "component.auditor-type=recipe-service-auditor"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("CustomerRestControllerV1 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class CustomerRestControllerV1IntegrationTest {

    private CreateCustomerRequestBody createCustomerRequestBody;
    private UpdateCustomerDetailsRequestBody updateCustomerDetailsRequestBody;
    private UpdateCustomerPasswordRequestBody updateCustomerPasswordRequestBody;
    private final CustomerRepositoryV1 repository;
    private final RestTestClient restTestClient;

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
    @DisplayName("createCustomer method should return HTTP CREATED when request body is valid")
    void createCustomer_whenRequestBodyIsValid_returnsHttpCreated() {
        createCustomerRequestBody.setUsername("NewUser");
        createCustomerRequestBody.setPassword("{noop}NewUser");
        restTestClient.post().uri("/api/v1/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(createCustomerRequestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("createCustomer method should return HTTP BAD REQUEST when request body is invalid")
    void createCustomer_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        restTestClient.post().uri("/api/v1/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(createCustomerRequestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("createCustomer method should return HTTP BAD REQUEST when request body is not provided")
    void createCustomer_whenRequestBodyIsNotProvided_returnsHttpBadRequest() {
        restTestClient.post().uri("/api/v1/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("createCustomer method should return HTTP BAD REQUEST when request body is empty")
    void createCustomer_whenRequestBodyIsEmpty_returnsHttpBadRequest() {
        restTestClient.post().uri("/api/v1/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body("{}")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("getCustomerDetails method should return HTTP UNAUTHORIZED when request is unauthenticated")
    void getCustomerDetails_whenRequestIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.get().uri("/api/v1/customers/details")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("getCustomerDetails method should return HTTP OK when request is authenticated")
    void getCustomerDetails_whenRequestIsAuthenticated_returnsHttpOk_andCustomerDetailsResponseBody() {
        final EntityExchangeResult<CustomerDetailsResponseBody> response = restTestClient.get().uri("/api/v1/customers/details")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(CustomerDetailsResponseBody.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);
        final CustomerDetailsResponseBody responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getCustomerImage method should return HTTP OK & Customer image when username exists")
    void getCustomerImage_whenUsernameExists_returnsHttpOk_andTheCustomerImage() {
        final String username = repository.findAll()
                .stream()
                .findAny()
                .orElseThrow()
                .getUsername();
        final EntityExchangeResult<byte[]> response = restTestClient.get().uri("/api/v1/customers/image/{username}", username)
                .exchange()
                .expectBody(new ParameterizedTypeReference<byte[]>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);
        final byte[] responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getCustomerImage method should return HTTP NOT FOUND when username does not exist")
    void getCustomerImage_whenUsernameDoesNotExist_returnsHttpNotFound() {
        final String nonExistentUsername = "nonExistentUsername";
        restTestClient.get().uri("/api/v1/customers/image/{username}", nonExistentUsername)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getCustomerImage method should return HTTP NOT FOUND when username is empty")
    void getCustomerImage_whenUsernameIsBlank_returnsHttpNotFound() {
        final String blankUsername = " ";
        restTestClient.get().uri("/api/v1/customers/image/{username}", blankUsername)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getCustomerImage method should return HTTP NOT FOUND when username is not provided")
    void getCustomerImage_whenUsernameIsNotProvided_returnsHttpNotFound() {
        restTestClient.get().uri("/api/v1/customers/image/{username}", "")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("searchCustomers should return page response of customer profiles & HTTP OK when query is provided")
    void searchCustomers_whenQueryIsProvided_returnsHttpOk_andCustomerProfilesPageResponse() {
        final EntityExchangeResult<PageResponse<CustomerProfileResponseBody>> response = restTestClient.get().uri("/api/v1/customers/search?query=user&page=0&size=10")
                .exchange()
                .expectBody(new ParameterizedTypeReference<PageResponse<CustomerProfileResponseBody>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);
        final PageResponse<CustomerProfileResponseBody> responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("searchCustomers should return page response of random customer profiles & HTTP OK when query is not provided")
    void searchCustomers_whenQueryIsNotProvided_returnsHttpOk_andRandomCustomerProfilesPageResponse() {
        final EntityExchangeResult<PageResponse<CustomerProfileResponseBody>> response = restTestClient.get().uri("/api/v1/customers/search?page=0&size=10")
                .exchange()
                .expectBody(new ParameterizedTypeReference<PageResponse<CustomerProfileResponseBody>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);
        final PageResponse<CustomerProfileResponseBody> responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getCustomerProfile method should return HTTP OK & Customer profile when username exists")
    void getCustomerProfile_whenUsernameExists_returnsHttpOk_andCustomerProfileResponseBody() {
        final String username = repository.findAll()
                .stream()
                .findAny()
                .orElseThrow()
                .getUsername();
        final EntityExchangeResult<CustomerProfileResponseBody> response = restTestClient.get().uri("/api/v1/customers/profile/{username}", username)
                .exchange()
                .expectBody(CustomerProfileResponseBody.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);
        final CustomerProfileResponseBody responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getCustomerProfile method should return HTTP NOT FOUND when username does not exist")
    void getCustomerProfile_whenUsernameDoesNotExist_returnsHttpNotFound() {
        final String nonExistentUsername = "nonExistentUsername";
        restTestClient.get().uri("/api/v1/customers/profile/{username}", nonExistentUsername)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getCustomerProfile method should return HTTP NOT FOUND when username is blank")
    void getCustomerProfile_whenUsernameIsBlank_returnsHttpNotFound() {
        final String blankUsername = " ";
        restTestClient.get().uri("/api/v1/customers/profile/{username}", blankUsername)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getCustomerProfile method should return HTTP NOT FOUND when username is not provided")
    void getCustomerProfile_whenUsernameIsNotProvided_returnsHttpNotFound() {
        restTestClient.get().uri("/api/v1/customers/profile/{username}", "")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("updateCustomerDetails method should return HTTP NO CONTENT when request body is valid")
    void updateCustomerDetails_whenRequestBodyIsValid_returnsHttpNoContent() {
        restTestClient.put().uri("/api/v1/customers/details")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(updateCustomerDetailsRequestBody)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("updateCustomerDetails method should return HTTP BAD REQUEST when request body is invalid")
    void updateCustomerDetails_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        restTestClient.put().uri("/api/v1/customers/details")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(new UpdateCustomerDetailsRequestBody())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("updateCustomerDetails method should return HTTP UNAUTHORIZED when user is unauthenticated")
    void updateCustomerDetails_whenRequestIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.put().uri("/api/v1/customers/details")
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateCustomerDetailsRequestBody)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("updateCustomerDetails method should return HTTP BAD REQUEST when request body is not provided")
    void updateCustomerDetails_whenRequestBodyIsNotProvided_returnsHttpBadRequest() {
        restTestClient.put().uri("/api/v1/customers/details")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("updateCustomerPassword method should return HTTP NO CONTENT when request body is valid")
    void updateCustomerPassword_whenRequestBodyIsValid_returnsHttpNoContent() {
        final Customer customer = repository.findByUsernameIgnoreCase("admin").orElseThrow();
        final String username = customer.getUsername();
        final String currentPassword = customer.getPassword().replace("{noop}", "");
        updateCustomerPasswordRequestBody.setOldPassword(currentPassword);
        restTestClient.put().uri("/api/v1/customers/password")
                .headers(headers -> headers.setBasicAuth(username, currentPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateCustomerPasswordRequestBody)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("updateCustomerPassword method should return HTTP BAD REQUEST when request body is invalid")
    void updateCustomerPassword_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        final Customer customer = repository.findByUsernameIgnoreCase("admin").orElseThrow();
        final String username = customer.getUsername();
        final String currentPassword = customer.getPassword().replace("{noop}", "");
        restTestClient.put().uri("/api/v1/customers/password")
                .headers(headers -> headers.setBasicAuth(username, currentPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UpdateCustomerPasswordRequestBody())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("updateCustomerPassword method should return HTTP BAD REQUEST when passwords do not match")
    void updateCustomerPassword_whenPasswordsDoNotMatch_returnsHttpBadRequest() {
        final Customer customer = repository.findByUsernameIgnoreCase("admin").orElseThrow();
        final String username = customer.getUsername();
        final String currentPassword = customer.getPassword().replace("{noop}", "");
        updateCustomerPasswordRequestBody.setOldPassword("wrongPassword");
        restTestClient.put().uri("/api/v1/customers/password")
                .headers(headers -> headers.setBasicAuth(username, currentPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateCustomerPasswordRequestBody)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("updateCustomerPassword method should return HTTP UNAUTHORIZED when user is unauthenticated")
    void updateCustomerPassword_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.put().uri("/api/v1/customers/password")
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateCustomerPasswordRequestBody)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("updateCustomerImage method should return HTTP UNSUPPORTED MEDIA TYPE when the image is not provided")
    void updateCustomerImage_whenImageIsNotProvided_returnsHttpUnsupportedMediaType() {
        restTestClient.put().uri("/api/v1/customers/image")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.IMAGE_JPEG)
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    @DisplayName("restoreDefaultImage method should return HTTP NO CONTENT when user is authenticated")
    void restoreDefaultImage_whenUserIsAuthenticated_returnsHttpNoContent() {
        restTestClient.put().uri("/api/v1/customers/image/restore")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("restoreDefaultImage method should return HTTP UNAUTHORIZED when user is unauthenticated")
    void restoreDefaultImage_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.put().uri("/api/v1/customers/image/restore")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("deleteCustomer method should return HTTP NO CONTENT when user is authenticated")
    void deleteCustomer_whenUserIsAuthenticated_returnsHttpOk() {
        restTestClient.delete().uri("/api/v1/customers")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("deleteCustomer method should return HTTP UNAUTHORIZED when user is unauthenticated")
    void deleteCustomer_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.delete().uri("/api/v1/customers")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }
}