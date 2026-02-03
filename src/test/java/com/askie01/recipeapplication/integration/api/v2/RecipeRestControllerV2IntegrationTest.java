package com.askie01.recipeapplication.integration.api.v2;

import com.askie01.recipeapplication.api.v2.RecipeRestControllerV2;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.dto.RecipeResponseBody;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.RecipeRepositoryV2;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebMvcTest(controllers = RecipeRestControllerV2.class)
@AutoConfigureDataJpa
@AutoConfigureRestTestClient
@Import(value = {
        JpaAuditingConfiguration.class,
        RecipeServiceV2Configuration.class,
        RecipeRequestBodyToRecipeMapperConfiguration.class,
        RecipeToRecipeResponseBodyMapperConfiguration.class,
        UserDetailsManagerConfiguration.class,
        PasswordEncoderConfiguration.class,
        SecurityFilterChainConfiguration.class
})
@TestPropertySource(properties = {
        "api.recipe.v2.enabled=true",
        "component.auditor-type=recipe-service-auditor",
        "component.service.recipe-v2=default",
        "component.mapper.recipe-request-body-to-recipe-type=default",
        "component.mapper.recipe-to-recipe-response-body-type=default",
        "component.manager.user-details=in-memory"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRestControllerV2 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRestControllerV2IntegrationTest {

    private RecipeRequestBody source;
    private final RecipeRepositoryV2 repository;
    private final RestTestClient restTestClient;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeRequestBody();
    }

    private static RecipeRequestBody getTestRecipeRequestBody() {
        final IngredientRequestBody ingredient = IngredientRequestBody.builder()
                .name("Test name in ingredient request body")
                .amount(10.2)
                .unit("Test unit int ingredient request body")
                .build();
        return RecipeRequestBody.builder()
                .name("Test recipe request body")
                .image(new byte[24])
                .description("Test description in recipe request body")
                .difficulty(Difficulty.MEDIUM)
                .categories(new HashSet<>(Set.of("Test category in recipe request body")))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(10.2)
                .cookingTime(10)
                .instructions("Test instructions in recipe request body")
                .build();
    }

    @Test
    @DisplayName("createRecipe method should return HttpCreated when request body is valid")
    void createRecipe_whenRequestBodyIsValid_returnsHttpCreated() {
        restTestClient.post().uri("/api/v2/recipes")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(source)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("createRecipe method should return HttpBadRequest when request body is invalid")
    void createRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        restTestClient.post().uri("/api/v2/recipes")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RecipeRequestBody())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("createRecipe method should return HttpBadRequest when request body is null")
    void createRecipe_whenRequestBodyIsNull_returnsHttpBadRequest() {
        restTestClient.post().uri("/api/v2/recipes")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("getRecipe method should return recipe response body when id exists")
    void getRecipe_whenIdExists_returnsRecipeResponseBody() {
        final Long id = repository.findAll().stream().findFirst().orElseThrow().getId();
        final EntityExchangeResult<RecipeResponseBody> response = restTestClient.get().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(RecipeResponseBody.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);

        final RecipeResponseBody responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getRecipe method should return HttpNotFound when id does not exist")
    void getRecipe_whenIdDoesNotExist_returnsHttpNotFound() {
        final Long id = Long.MAX_VALUE;
        restTestClient.get().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("getRecipe method should return HttpBadRequest when id is not a number")
    void getRecipe_whenIdIsNotNumber_returnsHttpBadRequest() {
        final String id = "notANumber";
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);

        final ErrorHttpResponse responseBody = response.getResponseBody();
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getRecipes method should return list of recipes when page number and page size are valid")
    void getRecipes_whenPageNumberAndPageSizeAreValid_returnsListOfRecipes() {
        final EntityExchangeResult<List<RecipeResponseBody>> response = restTestClient.get().uri("/api/v2/recipes?pageNumber=0&pageSize=10")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<RecipeResponseBody>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);

        final boolean notEmpty = !response.getResponseBody().isEmpty();
        assertTrue(notEmpty);
    }

    @Test
    @DisplayName("getRecipes method should return HttpBadRequest when page number is invalid")
    void getRecipes_whenPageNumberIsInvalid_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v2/recipes?pageNumber=notANumber&pageSize=10")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("getRecipes method should return HttpBadRequest when page size is invalid")
    void getRecipes_whenPageSizeIsInvalid_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v2/recipes?pageNumber=0&pageSize=notANumber")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("searchRecipes method should return list of recipes when text, page number and page size are valid")
    void searchRecipes_whenTextPageNumberAndPageSizeAreValid_returnsListOfRecipes() {
        final EntityExchangeResult<List<RecipeResponseBody>> response = restTestClient.get().uri("/api/v2/recipes/search?text=Blueberry&pageNumber=0&pageSize=10")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<RecipeResponseBody>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);

        final boolean notEmpty = !response.getResponseBody().isEmpty();
        assertTrue(notEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return empty list when text does not exist")
    void searchRecipes_whenTextDoesNotExist_returnsEmptyList() {
        final EntityExchangeResult<List<RecipeResponseBody>> response = restTestClient.get().uri("/api/v2/recipes/search?text=123&pageNumber=0&pageSize=10")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<RecipeResponseBody>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);

        final boolean isEmpty = response.getResponseBody().isEmpty();
        assertTrue(isEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return list of random recipes when text is null")
    void searchRecipes_whenTextIsNull_returnsListOfRandomRecipes() {
        final EntityExchangeResult<List<RecipeResponseBody>> response = restTestClient.get().uri("/api/v2/recipes/search?text=&pageNumber=0&pageSize=10")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<RecipeResponseBody>>() {
                })
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.OK, responseStatus);

        final boolean notEmpty = !response.getResponseBody().isEmpty();
        assertTrue(notEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return HttpBadRequest when page number is invalid")
    void searchRecipes_whenPageNumberIsInvalid_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v2/recipes/search?text=Blueberry&pageNumber=notANumber&pageSize=10")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("searchRecipes method should return HttpBadRequest when page size is invalid")
    void searchRecipes_whenPageSizeIsInvalid_returnsHttpBadRequest() {
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.get().uri("/api/v2/recipes/search?text=Blueberry&pageNumber=0&pageSize=notANumber")
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }

    @Test
    @DisplayName("updateRecipe method should return HttpOk when id exists and request body is valid")
    void updateRecipe_whenIdExistsAndRequestBodyIsValid_returnsHttpOk() {
        final Long id = repository.findAll().stream().findFirst().orElseThrow().getId();
        restTestClient.put().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(source)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("updateRecipe method should return HttpNotFound when id does not exist")
    void updateRecipe_whenIdDoesNotExist_returnsHttpNotFound() {
        final Long id = Long.MAX_VALUE;
        restTestClient.put().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(source)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("updateRecipe method should return HttpBadRequest when request body is invalid")
    void updateRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() {
        final Long id = repository.findAll().stream().findFirst().orElseThrow().getId();
        restTestClient.put().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RecipeRequestBody())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("deleteRecipe method should return HttpOk when id exists")
    void deleteRecipe_whenIdExists_returnsHttpOk() {
        final Long id = repository.findAll().stream().findFirst().orElseThrow().getId();
        restTestClient.delete().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("deleteRecipe method should return HttpOk when id does not exist")
    void deleteRecipe_whenIdDoesNotExist_returnsHttpOk() {
        final Long id = Long.MAX_VALUE;
        restTestClient.delete().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("deleteRecipe method should return HttpBadRequest when id is not a number")
    void deleteRecipe_whenIdIsNotNumber_returnsHttpBadRequest() {
        final String id = "notANumber";
        final EntityExchangeResult<ErrorHttpResponse> response = restTestClient.delete().uri("/api/v2/recipes/{id}", id)
                .headers(headers -> headers.setBasicAuth("user", "user"))
                .exchange()
                .expectBody(ErrorHttpResponse.class)
                .returnResult();
        final HttpStatusCode responseStatus = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus);
    }
}