package com.askie01.recipeapplication.integration.api.v1;

import com.askie01.recipeapplication.api.v1.RecipeRestControllerV1;
import com.askie01.recipeapplication.configuration.RandomRecipeDTOUnsavedEntityTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.RecipeRestControllerV1TestConfiguration;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.factory.RecipeDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.repository.RecipeRepository;
import com.askie01.recipeapplication.response.ErrorHttpResponse;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(controllers = RecipeRestControllerV1.class)
@AutoConfigureDataJpa
@AutoConfigureRestTestClient
@Import(value = {
        RecipeRestControllerV1TestConfiguration.class,
        RandomRecipeDTOUnsavedEntityTestFactoryDefaultTestConfiguration.class
})
@TestPropertySource(locations = "classpath:recipe-rest-controller-v1-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRestControllerV1 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRestControllerV1IntegrationTest {

    private RecipeDTO source;
    private final RecipeRepository repository;
    private final RestTestClient restTestClient;
    private final RecipeDTOUnsavedEntityTestFactory factory;

    @BeforeEach
    void setUp() {
        this.source = factory.createRecipeDTO();
    }

    @Test
    @DisplayName("createRecipe method should return saved recipe in DTO format when request body is valid")
    void createRecipe_whenRequestBodyIsValid_returnsSavedRecipeDTO() {
        final EntityExchangeResult<RecipeDTO> response = restTestClient.post().uri("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(source)
                .exchange()
                .expectBody(RecipeDTO.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        final RecipeDTO responseBody = response.getResponseBody();
        assertEquals(HttpStatus.CREATED, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("createRecipe method should return HTTP BadRequest status code when request body is invalid")
    void createRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.post().uri("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RecipeDTO())
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("createRecipe method should return HTTP BadRequest status code when request body is null")
    void createRecipe_whenRequestBodyIsNull_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.post().uri("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("getRecipe method should return recipe in DTO format when id exists")
    void getRecipe_whenIdExists_returnsRecipeDTOWithThatId() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        final EntityExchangeResult<RecipeDTO> response = restTestClient.get().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectBody(RecipeDTO.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        final RecipeDTO responseBody = response.getResponseBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertEquals(id, responseBody.getId());
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getRecipe method should return HTTP Not Found status code when id does not exist")
    void getRecipe_whenIdDoesNotExist_returnsHttpNotFound() {
        final Long id = Long.MAX_VALUE;
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    @DisplayName("getRecipe method should return HTTP BadRequest status code when id is not a number")
    void getRecipe_whenIdIsNotNumber_returnsHttpBadRequest() {
        final String id = "notANumber";
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("getRecipes method should return list of RecipeDTO objects.")
    void getRecipes_returnsListOfRecipeDTO() {
        final EntityExchangeResult<List<RecipeDTO>> response = restTestClient.get().uri("/api/v1/recipes")
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<RecipeDTO>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        final List<RecipeDTO> responseBody = response.getResponseBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("updateRecipe method should return updated recipe in DTO format when request body is valid")
    void updateRecipe_whenRequestBodyIsValid_returnsUpdatedRecipeDTO() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        source.setId(id);
        final EntityExchangeResult<RecipeDTO> response = restTestClient.put().uri("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(source)
                .exchange()
                .expectBody(RecipeDTO.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        final RecipeDTO responseBody = response.getResponseBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("updateRecipe method should return HTTP BadRequest status code when request body is invalid")
    void updateRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.put().uri("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RecipeDTO())
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("updateRecipe method should return HTTP BadRequest status code when request body is not present")
    void updateRecipe_whenRequestBodyIsNotPresent_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.put().uri("/api/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("deleteRecipe method should return deleted recipe in DTO format when id is valid")
    void deleteRecipe_whenIdIsValid_returnsDeletedRecipeDTO() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        final EntityExchangeResult<RecipeDTO> response = restTestClient.delete().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectBody(RecipeDTO.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        final RecipeDTO responseBody = response.getResponseBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP Not Found status code when id is invalid")
    void deleteRecipe_whenIdIsInvalid_returnsHttpNotFound() {
        final Long id = Long.MAX_VALUE;
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.delete().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP BadRequest status code when id is not a number")
    void deleteRecipe_whenIsIsNotNumber_returnsHttpBadRequest() {
        final String id = "notANumber";
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.delete().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }
}