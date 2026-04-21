package com.askie01.recipeapplication.integration.aspect;

import com.askie01.recipeapplication.aspect.RecipeRestControllerV3LoggingAspect;
import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import com.askie01.recipeapplication.service.RecipeServiceV3;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureRestTestClient
@TestPropertySource(properties = "api.recipe.v3.enabled=true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRestControllerV3LoggingAspect integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRestControllerV3LoggingAspectIntegrationTest {

    @MockitoSpyBean
    private final RecipeRestControllerV3LoggingAspect aspect;

    @MockitoBean
    private final RecipeServiceV3 service;
    private final RecipeRepositoryV3 recipeRepository;
    private final CustomerRepositoryV1 customerRepository;

    private final MockMvc mockMvc;
    private final RestTestClient restTestClient;

    private CustomerRecipeRequestBody requestBody;

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
    @DisplayName("aspect should log two statements when createRecipe is called with authenticated user and valid request body")
    void aspect_whenCreateRecipeIsCalledWithAuthenticatedUser_andValidRequestBody_logsTwoStatements() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        restTestClient.post().uri("/api/v3/recipes")
                .headers(headers -> headers.setBasicAuth(username, password))
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isCreated();
        verify(aspect, times(1)).logBeforeCreateRecipe(any());
        verify(aspect, times(1)).logAfterCreateRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statements when createRecipe is called with authenticated user and invalid request body")
    void aspect_whenCreateRecipeIsCalledWithAuthenticatedUser_andInvalidRequestBody_skipsLogStatements() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        restTestClient.post().uri("/api/v3/recipes")
                .headers(headers -> headers.setBasicAuth(username, password))
                .body(new CustomerRecipeRequestBody())
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when createRecipe call throws an exception")
    void aspect_whenCreateRecipeCallThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service)
                .createRecipe(
                        any(String.class),
                        any(CustomerRecipeRequestBody.class)
                );
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        restTestClient.post().uri("/api/v3/recipes")
                .headers(headers -> headers.setBasicAuth(username, password))
                .body(requestBody)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeCreateRecipe(any());
        verify(aspect, times(1)).logAndRethrowCreateRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getRecipe is called with existing id")
    void aspect_whenGetRecipeIsCalledWithExistingId_logsTwoStatements() {
        final Long id = recipeRepository.findAll().getFirst().getId();
        final Recipe recipe = Recipe.builder()
                .id(id)
                .categories(new HashSet<>())
                .ingredients(new HashSet<>())
                .build();
        when(service.getRecipe(any(Long.class)))
                .thenReturn(recipe);
        restTestClient.get().uri("/api/v3/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetRecipe(any());
        verify(aspect, times(1)).logAfterGetRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getRecipe is called with non-existing id")
    void aspect_whenGetRecipeIsCalledWithNonExistingId_logsTwoStatements() throws Throwable {
        when(service.getRecipe(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        final Long id = Long.MAX_VALUE;
        restTestClient.get().uri("/api/v3/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeGetRecipe(any());
        verify(aspect, times(1)).logAndRethrowGetRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getRecipeImage is called with existing id")
    void aspect_whenGetRecipeImageIsCalledWithExistingId_logsTwoStatements() {
        final Long id = recipeRepository.findAll().getFirst().getId();
        restTestClient.get().uri("/api/v3/recipes/{id}/image", id)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetRecipeImage(any());
        verify(aspect, times(1)).logAfterGetRecipeImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getRecipeImage is called with non-existing id")
    void aspect_whenGetRecipeImageIsCalledWithNonExistingId_logsTwoStatements() throws Throwable {
        when(service.getRecipeImage(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        final Long id = Long.MAX_VALUE;
        restTestClient.get().uri("/api/v3/recipes/{id}/image", id)
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeGetRecipeImage(any());
        verify(aspect, times(1)).logAndRethrowGetRecipeImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getUserRecipes is called with existing username")
    void aspect_whenGetUserRecipesIsCalledWithExistingUsername_logsTwoStatements() {
        when(service.getCustomerRecipes(
                any(String.class),
                any(Pageable.class)))
                .thenReturn(Page.empty());
        final String username = customerRepository.findAll().getFirst().getUsername();
        restTestClient.get().uri("/api/v3/recipes?username={username}", username)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetUserRecipes(any());
        verify(aspect, times(1)).logAfterGetUserRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when getUserRecipes call throws an exception")
    void aspect_whenGetUserRecipesCallThrowsAnException_logsTwoStatements() throws Throwable {
        when(service.getCustomerRecipes(
                any(String.class),
                any(Pageable.class)))
                .thenThrow(UsernameNotFoundException.class);
        final String username = customerRepository.findAll().getFirst().getUsername();
        restTestClient.get().uri("/api/v3/recipes?username={username}", username)
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeGetUserRecipes(any());
        verify(aspect, times(1)).logAndRethrowGetUserRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when searchRecipes is called")
    void aspect_whenSearchRecipesIsCalled_logsTwoStatements() {
        when(service.searchRecipes(
                any(String.class),
                any(Pageable.class)))
                .thenReturn(Page.empty());
        final String query = recipeRepository.findAll().getFirst().getName();
        restTestClient.get().uri("/api/v3/recipes/search?query={query}", query)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeSearchUserRecipes(any());
        verify(aspect, times(1)).logAfterSearchUserRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when searchRecipes call throws an exception")
    void aspect_whenSearchRecipesCallThrowsAnException_logsTwoStatements() throws Throwable {
        when(service.searchRecipes(
                any(String.class),
                any(Pageable.class)))
                .thenThrow(RuntimeException.class);
        final String query = recipeRepository.findAll().getFirst().getName();
        restTestClient.get().uri("/api/v3/recipes/search?query={query}", query)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeSearchUserRecipes(any());
        verify(aspect, times(1)).logAndRethrowSearchUserRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when updateRecipeDetails is called")
    void aspect_whenUpdateRecipeDetailsIsCalled_logsTwoStatements() {
        final Long id = recipeRepository.findAll().getFirst().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(requestBody)
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeUpdateRecipeDetails(any());
        verify(aspect, times(1)).logAfterUpdateRecipeDetails(any());
    }

    @Test
    @DisplayName("aspect should log two statements when updateRecipeDetails call throws an exception")
    void aspect_whenUpdateRecipeDetailsCallThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service)
                .updateRecipe(
                        any(String.class),
                        any(Long.class),
                        any(CustomerRecipeRequestBody.class)
                );
        restTestClient.put().uri("/api/v3/recipes/{id}", 1L)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .body(requestBody)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeUpdateRecipeDetails(any());
        verify(aspect, times(1)).logAndRethrowUpdateRecipeDetails(any());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("aspect should log two statements when updateRecipeImage is called")
    void aspect_whenUpdateRecipeImageIsCalled_logsTwoStatements() throws Exception {
        final Long id = recipeRepository.findAll().getFirst().getId();
        final MockMultipartFile image = new MockMultipartFile(
                "image",
                "default-recipe.png",
                MediaType.IMAGE_PNG_VALUE,
                new ClassPathResource("static/default-recipe.png").getContentAsByteArray()
        );
        mockMvc.perform(multipart(HttpMethod.PUT, "/api/v3/recipes/{id}/image", id)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .file(image))
                .andExpect(status().isNoContent());
        verify(aspect, times(1)).logBeforeUpdateRecipeImage(any());
        verify(aspect, times(1)).logAfterUpdateRecipeImage(any());
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    @DisplayName("aspect should log two statements when updateRecipeImage call throws an exception")
    void aspect_whenUpdateRecipeImageCallThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service)
                .updateImage(
                        any(String.class),
                        any(Long.class),
                        any(MultipartFile.class)
                );
        final MockMultipartFile image = new MockMultipartFile(
                "image",
                "default-recipe.png",
                MediaType.IMAGE_PNG_VALUE,
                new ClassPathResource("static/default-recipe.png").getContentAsByteArray()
        );
        mockMvc.perform(multipart(HttpMethod.PUT, "/api/v3/recipes/{id}/image", 1L)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .file(image))
                .andExpect(status().is5xxServerError());
        verify(aspect, times(1)).logBeforeUpdateRecipeImage(any());
        verify(aspect, times(1)).logAndRethrowUpdateRecipeImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when restoreDefaultRecipeImage is called")
    void aspect_whenRestoreDefaultRecipeImageIsCalled_logsTwoStatements() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}/image/restore", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeRestoreDefaultRecipeImage(any());
        verify(aspect, times(1)).logAfterRestoreDefaultRecipeImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when restoreDefaultRecipeImage call throws an exception")
    void aspect_whenRestoreDefaultRecipeImageCallThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service)
                .restoreDefaultImage(
                        any(String.class),
                        any(Long.class)
                );
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        restTestClient.put().uri("/api/v3/recipes/{id}/image/restore", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeRestoreDefaultRecipeImage(any());
        verify(aspect, times(1)).logAndRethrowRestoreDefaultRecipeImage(any());
    }

    @Test
    @DisplayName("aspect should log two statements when deleteRecipe is called")
    void aspect_whenDeleteRecipeIsCalled_logsTwoStatements() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        restTestClient.delete().uri("/api/v3/recipes/{id}", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .isNoContent();
        verify(aspect, times(1)).logBeforeDeleteRecipe(any());
        verify(aspect, times(1)).logAfterDeleteRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when deleteRecipe call throws an exception")
    void aspect_whenDeleteRecipeCallThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service)
                .deleteRecipe(
                        any(String.class),
                        any(Long.class)
                );
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final String password = customer.getPassword().replace("{noop}", "");
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        restTestClient.delete().uri("/api/v3/recipes/{id}", recipeId)
                .headers(headers -> headers.setBasicAuth(username, password))
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeDeleteRecipe(any());
        verify(aspect, times(1)).logAndRethrowDeleteRecipe(any());
    }
}