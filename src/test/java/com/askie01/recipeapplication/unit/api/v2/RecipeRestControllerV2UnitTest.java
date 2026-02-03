package com.askie01.recipeapplication.unit.api.v2;

import com.askie01.recipeapplication.api.v2.RecipeRestControllerV2;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.dto.RecipeResponseBody;
import com.askie01.recipeapplication.exception.GlobalExceptionHandler;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.RecipeToRecipeResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.service.RecipeServiceV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import tools.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecipeRestControllerV2 unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RecipeRestControllerV2UnitTest {

    private RecipeRequestBody source;
    private MockMvc mockMvc;

    @Mock
    private RecipeServiceV2 service;

    @Mock
    private RecipeToRecipeResponseBodyMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeRequestBody();
        this.mockMvc = setUpMockMVC();
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

    private MockMvc setUpMockMVC() {
        final RecipeRestControllerV2 controller = new RecipeRestControllerV2(service, mapper);
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    @DisplayName("createRecipe method should return HttpCreated when request body is valid")
    void createRecipe_whenRequestBodyIsValid_returnsHttpCreated() throws Exception {
        mockMvc.perform(post("/api/v2/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(source)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("createRecipe method should return HttpBadRequest when request body is invalid")
    void createRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(post("/api/v2/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new RecipeRequestBody())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("createRecipe method should return HttpBadRequest when request body is null")
    void createRecipe_whenRequestBodyIsNull_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(post("/api/v2/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getRecipe method should return recipe response body when id exists")
    void getRecipe_whenIdExists_returnsRecipeResponseBody() throws Exception {
        when(service.getRecipe(any(Long.class))).thenReturn(new Recipe());
        when(mapper.mapToDTO(any(Recipe.class))).thenReturn(RecipeResponseBody.builder().id(1L).build());
        mockMvc.perform(get("/api/v2/recipes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("getRecipe method should return HttpNotFound when id does not exist")
    void getRecipe_whenIdDoesNotExist_returnsHttpNotFound() throws Exception {
        when(service.getRecipe(any(Long.class))).thenThrow(RecipeNotFoundException.class);
        final Long id = Long.MAX_VALUE;
        mockMvc.perform(get("/api/v2/recipes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getRecipe method should return HttpBadRequest when id is not a number")
    void getRecipe_whenIdIsNotNumber_returnsHttpBadRequest() throws Exception {
        final String id = "notANumber";
        mockMvc.perform(get("/api/v2/recipes/{id}", id))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getRecipes method should return list of recipes when page number and page size are valid")
    void getRecipes_whenPageNumberAndPageSizeAreValid_returnsListOfRecipes() throws Exception {
        when(service.getRecipes(any(Integer.class), any(Integer.class)))
                .thenReturn(List.of(new Recipe()));
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeResponseBody.builder().id(1L).build());
        mockMvc.perform(get("/api/v2/recipes")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("getRecipes method should return HttpBadRequest when page number is invalid")
    void getRecipes_whenPageNumberIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(get("/api/v2/recipes")
                        .param("pageNumber", "notANumber")
                        .param("pageSize", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getRecipes method should return HttpBadRequest when page size is invalid")
    void getRecipes_whenPageSizeIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(get("/api/v2/recipes")
                        .param("pageNumber", "0")
                        .param("pageSize", "notANumber"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("searchRecipes method should return list of recipes when text, page number and page size are valid")
    void searchRecipes_whenTextPageNumberAndPageSizeAreValid_returnsListOfRecipes() throws Exception {
        when(service.searchRecipes(any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn(List.of(new Recipe()));
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeResponseBody.builder().id(1L).build());
        mockMvc.perform(get("/api/v2/recipes/search")
                        .param("text", "Blueberry")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("searchRecipes method should return empty list when text does not exist")
    void searchRecipes_whenTextDoesNotExist_returnsEmptyList() throws Exception {
        when(service.searchRecipes(any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v2/recipes/search")
                        .param("text", "123")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("searchRecipes method should return list of random recipes when text is null")
    void searchRecipes_whenTextIsNull_returnsListOfRandomRecipes() throws Exception {
        when(service.searchRecipes(any(String.class), any(Integer.class), any(Integer.class)))
                .thenReturn(List.of(new Recipe()));
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeResponseBody.builder().id(1L).build());
        mockMvc.perform(get("/api/v2/recipes/search")
                        .param("text", "")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("searchRecipes method should return HttpBadRequest when page number is invalid")
    void searchRecipes_whenPageNumberIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(get("/api/v2/recipes/search")
                        .param("text", "Bluberry")
                        .param("pageNumber", "notANumber")
                        .param("pageSize", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("searchRecipes method should return HttpBadRequest when page size is invalid")
    void searchRecipes_whenPageSizeIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(get("/api/v2/recipes/search")
                        .param("text", "Blueberry")
                        .param("pageNumber", "0")
                        .param("pageSize", "notANumber"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("updateRecipe method should return HttpOk when id exists and request body is valid")
    void updateRecipe_whenIdExistsAndRequestBodyIsValid_returnsHttpOk() throws Exception {
        mockMvc.perform(put("/api/v2/recipes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(source)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("updateRecipe method should return HttpNotFound when id does not exist")
    void updateRecipe_whenIdDoesNotExist_returnsHttpNotFound() throws Exception {
        final Long id = Long.MAX_VALUE;
        doThrow(RecipeNotFoundException.class)
                .when(service).updateRecipe(any(Long.class), any(RecipeRequestBody.class));
        mockMvc.perform(put("/api/v2/recipes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(source)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateRecipe method should return HttpBadRequest when request body is invalid")
    void updateRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(put("/api/v2/recipes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new RecipeRequestBody())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deleteRecipe method should return HttpOk when id exists")
    void deleteRecipe_whenIdExists_returnsHttpOk() throws Exception {
        mockMvc.perform(delete("/api/v2/recipes/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deleteRecipe method should return HttpOk when id does not exist")
    void deleteRecipe_whenIdDoesNotExist_returnsHttpOk() throws Exception {
        final Long id = Long.MAX_VALUE;
        mockMvc.perform(delete("/api/v2/recipes/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deleteRecipe method should return HttpBadRequest when id is not a number")
    void deleteRecipe_whenIdIsNotNumber_returnsHttpBadRequest() throws Exception {
        final String id = "notANumber";
        mockMvc.perform(delete("/api/v2/recipes/{id}", id))
                .andExpect(status().isBadRequest());
    }
}