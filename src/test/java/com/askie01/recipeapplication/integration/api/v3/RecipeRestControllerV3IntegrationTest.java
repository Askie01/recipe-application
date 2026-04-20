package com.askie01.recipeapplication.integration.api.v3;

import com.askie01.recipeapplication.api.v3.RecipeRestControllerV3;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecipeRestControllerV3.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureDataJpa
@AutoConfigureRestTestClient
@Import(value = {
        RecipeServiceV3Configuration.class,
        CustomerRecipeRequestBodyToRecipeMapperConfiguration.class,
        RecipeToSearchRecipeResponseBodyMapperConfiguration.class,
        RecipeToCustomerRecipeResponseBodyMapperConfiguration.class,
        UserDetailsServiceConfiguration.class,
        CustomerToUserDetailsMapperConfiguration.class,
        SecurityFilterChainConfiguration.class,
        PasswordEncoderConfiguration.class,
        JpaAuditingConfiguration.class
})
@TestPropertySource(properties = {
        "api.recipe.v3.enabled=true",
        "component.service.recipe-v3=default",
        "component.mapper.customer-recipe-request-body-to-recipe-type=default",
        "component.mapper.recipe-to-search-recipe-response-body=default",
        "component.mapper.recipe-to-customer-recipe-response-body=default",
        "component.service.user-details=default",
        "component.mapper.customer-to-user-details=default",
        "component.auditor-type=recipe-service-auditor",
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRestControllerV3 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRestControllerV3IntegrationTest {

    private CustomerRecipeRequestBody requestBody;
    private final RecipeRepositoryV3 recipeRepository;
    private final CustomerRepositoryV1 customerRepository;
    private final MockMvc mockMvc;
    private final RestTestClient restTestClient;

    @BeforeEach
    void setUp() {
        this.requestBody = getTestCustomerRecipeRequestBody();
    }

    private static CustomerRecipeRequestBody getTestCustomerRecipeRequestBody() {
        return CustomerRecipeRequestBody.builder()
                .name("Customer recipe name")
                .description("Customer recipe description")
                .difficulty(Difficulty.EASY)
                .categories(new HashSet<>(Set.of(
                        "First category",
                        "Second category"))
                )
                .ingredients(new HashSet<>(Set.of(
                        new IngredientRequestBody("First ingredient", 10.0d, "First unit"),
                        new IngredientRequestBody("Second ingredient", 20.0d, "Second unit")
                )))
                .servings(10.0d)
                .cookingTime(10)
                .instructions("Customer recipe instructions")
                .build();
    }

    @Test
    @DisplayName("createRecipe method should return HTTP created when user is authenticated and request body is valid")
    void createRecipe_whenUserIsAuthenticated_andRequestBodyIsValid_returnsHttpCreated() {
        restTestClient.post().uri("/api/v3/recipes")
                .headers(header -> header.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("createRecipe method should return HTTP unauthorized when user is unauthenticated")
    void createRecipe_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.post().uri("/api/v3/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("createRecipe method should return HTTP internal server error when user is authenticated and request body is invalid")
    void createRecipe_whenUserIsAuthenticated_andRequestBodyIsInvalid_returnsHttpInternalServerError() {
        restTestClient.post().uri("/api/v3/recipes")
                .headers(header -> header.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CustomerRecipeRequestBody())
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("createRecipe method should return HTTP bad request when user is authenticated and request body is not provided")
    void createRecipe_whenUserIsAuthenticated_andRequestBodyIsNotProvided_returnsHttpBadRequest() {
        restTestClient.post().uri("/api/v3/recipes")
                .headers(header -> header.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("getRecipe method should return recipe when recipe with given id exists")
    void getRecipe_whenRecipeIdExists_returnsRecipeWithThatId() {
        final Long recipeId = recipeRepository.findAll().stream().findAny().orElseThrow().getId();
        final EntityExchangeResult<CustomerRecipeResponseBody> response = restTestClient.get().uri("/api/v3/recipes/{id}", recipeId)
                .exchange()
                .returnResult(CustomerRecipeResponseBody.class);
        assertNotNull(response.getResponseBody());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("getRecipe method should return HTTP not found when recipe id does not exist")
    void getRecipe_whenRecipeIdDoesNotExist_returnsHttpNotFound() {
        final Long nonExistentRecipeId = Long.MAX_VALUE;
        restTestClient.get().uri("/api/v3/recipes/{id}", nonExistentRecipeId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getRecipeImage method should return recipe image when recipe with given id exists")
    void getRecipeImage_whenRecipeIdExists_returnsRecipeImageWithThatId() {
        final Long recipeId = recipeRepository.findAll().stream().findAny().orElseThrow().getId();
        final EntityExchangeResult<byte[]> response = restTestClient.get().uri("/api/v3/recipes/{id}/image", recipeId)
                .exchange()
                .returnResult(byte[].class);
        assertNotNull(response.getResponseBody());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("getRecipeImage method should return HTTP not found when recipe with given id does not exist")
    void getRecipeImage_whenRecipeIdDoesNotExist_returnsHttpNotFound() {
        final Long nonExistentRecipeId = Long.MAX_VALUE;
        restTestClient.get().uri("/api/v3/recipes/{id}/image", nonExistentRecipeId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getRecipeImage method should return HTTP bad request when recipe id is not provided")
    void getRecipeImage_whenRecipeIdIsNotProvided_returnsHttpBadRequest() {
        restTestClient.get().uri("/api/v3/recipes/image")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("getUserRecipes method should return customer recipes page when customer with given username exists")
    void getUserRecipes_whenUsernameExists_returnsCustomerRecipesPage() {
        final String username = customerRepository.findAll().stream().findAny().orElseThrow().getUsername();
        final EntityExchangeResult<PageResponse<CustomerRecipeResponseBody>> response = restTestClient
                .get()
                .uri("/api/v3/recipes?username={username}", username)
                .exchange()
                .returnResult(new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getResponseBody());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("getUserRecipes method should return empty page when customer with given username does not exist")
    void getUserRecipes_whenUsernameDoesNotExist_returnsEmptyPage() {
        final String nonExistentUsername = "nonExistentUsername";
        final EntityExchangeResult<PageResponse<CustomerRecipeResponseBody>> response = restTestClient
                .get()
                .uri("/api/v3/recipes?username={username}", nonExistentUsername)
                .exchange()
                .returnResult(new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getResponseBody());
        assertTrue(response.getResponseBody().getItems().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("getUserRecipes method should return empty page when username is not provided")
    void getUserRecipes_whenUsernameIsNotProvided_returnsEmptyPage() {
        final EntityExchangeResult<PageResponse<CustomerRecipeResponseBody>> response = restTestClient
                .get()
                .uri("/api/v3/recipes")
                .exchange()
                .returnResult(new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getResponseBody());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("searchRecipes method should return recipes page when query matches recipes")
    void searchRecipes_whenQueryMatchesRecipes_returnsRecipesPage() {
        final String query = recipeRepository.findAll().stream().findAny().orElseThrow().getName();
        final EntityExchangeResult<PageResponse<SearchRecipeResponseBody>> response = restTestClient
                .get()
                .uri("/api/v3/recipes/search?query={query}", query)
                .exchange()
                .returnResult(new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getResponseBody());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("searchRecipes method should return empty page when query does not match recipes")
    void searchRecipes_whenQueryDoesNotMatchRecipes_returnsEmptyPage() {
        final String query = "nonExistentQuery";
        final EntityExchangeResult<PageResponse<SearchRecipeResponseBody>> response = restTestClient
                .get()
                .uri("/api/v3/recipes/search?query={query}", query)
                .exchange()
                .returnResult(new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getResponseBody());
        assertTrue(response.getResponseBody().getItems().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("searchRecipes method should return random result page when query is not provided")
    void searchRecipes_whenQueryIsNotProvided_returnsRandomResultPage() {
        final EntityExchangeResult<PageResponse<SearchRecipeResponseBody>> response = restTestClient
                .get()
                .uri("/api/v3/recipes/search")
                .exchange()
                .returnResult(new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getResponseBody());
        assertFalse(response.getResponseBody().getItems().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    @DisplayName("updateRecipeDetails method should return HTTP no content when user is authenticated, recipe id exists, and request body is valid")
    void updateRecipeDetails_whenUserIsAuthenticated_recipeIdExists_andRequestBodyIsValid_returnsHttpNoContent() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findAny().orElseThrow().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("updateRecipeDetails method should return HTTP unauthorized when user is unauthenticated")
    void updateRecipeDetails_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.put().uri("/api/v3/recipes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("updateRecipeDetails method should return HTTP not found when user is authenticated, but recipe id does not exist")
    void updateRecipeDetails_whenUserIsAuthenticated_andRecipeIdDoesNotExist_returnsHttpNotFound() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long nonExistentRecipeId = Long.MAX_VALUE;
        restTestClient.put().uri("/api/v3/recipes/{id}", nonExistentRecipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("updateRecipeDetails method should return HTTP internal server error when user is authenticated, recipe id exists, but request body is invalid")
    void updateRecipeDetails_whenUserIsAuthenticated_andRecipeIdExists_andRequestBodyIsInvalid_returnsHttpInternalServerError() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findAny().orElseThrow().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new CustomerRecipeRequestBody())
                .exchange()
                .expectStatus()
                .is5xxServerError();
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("updateRecipeImage method should return HTTP no content when user is authenticated, recipe id exists, and request body is valid")
    void updateRecipeImage_whenUserIsAuthenticated_andRecipeIdExists_andImageIsValid_returnsHttpNoContent() throws Exception {
        final Long recipeId = customerRepository
                .findByUsernameIgnoreCase("user")
                .orElseThrow()
                .getRecipes()
                .stream()
                .findAny()
                .orElseThrow()
                .getId();
        final MockMultipartFile image = new MockMultipartFile(
                "image",
                "default-recipe.png",
                MediaType.IMAGE_PNG_VALUE,
                new ClassPathResource("static/default-recipe.png").getContentAsByteArray()
        );
        mockMvc.perform(multipart(HttpMethod.PUT, "/api/v3/recipes/{id}/image", recipeId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .file(image))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("updateRecipeImage method should return HTTP unauthorized when user is unauthenticated")
    void updateRecipeImage_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.put().uri("/api/v3/recipes/{id}/image", 1L)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(new ClassPathResource("static/default-recipe.png"))
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("updateRecipeImage method should return HTTP not found when user is authenticated, but recipe id does not exist")
    void updateRecipeImage_whenUserIsAuthenticated_andRecipeIdDoesNotExist_returnsHttpNotFound() throws Exception {
        final Long recipeId = Long.MAX_VALUE;
        final MockMultipartFile image = new MockMultipartFile(
                "image",
                "default-recipe.png",
                MediaType.IMAGE_PNG_VALUE,
                new ClassPathResource("static/default-recipe.png").getContentAsByteArray()
        );
        mockMvc.perform(multipart(HttpMethod.PUT, "/api/v3/recipes/{id}/image", recipeId)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .file(image))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateRecipeImage method should return HTTP bad request when user is authenticated, recipe id exists, but request body is invalid")
    void updateRecipeImage_whenUserIsAuthenticated_andRecipeIdExists_andImageIsInvalid_returnsHttpBadRequest() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findAny().orElseThrow().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}/image", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body("Plain text")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("restoreDefaultRecipeImage method should return HTTP no content when user is authenticated, and recipe id exists")
    void restoreDefaultRecipeImage_whenUserIsAuthenticated_andRecipeIdExists_returnsHttpNoContent() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findAny().orElseThrow().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}/image/restore", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("restoreDefaultRecipeImage method should return HTTP unauthorized when user is unauthenticated")
    void restoreDefaultRecipeImage_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.put().uri("/api/v3/recipes/{id}/image/restore", 1L)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("restoreDefaultRecipeImage method should return HTTP not found when user is authenticated, but recipe id does not exist")
    void restoreDefaultRecipeImage_whenUserIsAuthenticated_andRecipeIdDoesNotExist_returnsHttpNotFound() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = Long.MAX_VALUE;
        restTestClient.put().uri("/api/v3/recipes/{id}/image/restore", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP no content when user is authenticated, and recipe id exists")
    void deleteRecipe_whenUserIsAuthenticated_andRecipeIdExists_returnsHttpNoContent() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findAny().orElseThrow().getId();
        restTestClient.delete().uri("/api/v3/recipes/{id}", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP unauthorized when user is unauthenticated")
    void deleteRecipe_whenUserIsUnauthenticated_returnsHttpUnauthorized() {
        restTestClient.delete().uri("/api/v3/recipes/{id}", 1L)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP not found when user is authenticated, but recipe id does not exist")
    void deleteRecipe_whenUserIsAuthenticated_andRecipeIdDoesNotExist_returnsHttpNotFound() {
        final Customer customer = customerRepository.findByUsernameIgnoreCase("user").orElseThrow();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long nonExistentRecipeId = Long.MAX_VALUE;
        restTestClient.delete().uri("/api/v3/recipes/{id}", nonExistentRecipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}