package com.askie01.recipeapplication.unit.api.v1;

import com.askie01.recipeapplication.api.v1.RecipeRestControllerV1;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.exception.GlobalExceptionHandler;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.RecipeToRecipeDTOMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.service.RecipeServiceV1;
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

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecipeRestControllerV1 unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RecipeRestControllerV1UnitTest {

    private RecipeDTO source;
    private MockMvc mockMvc;

    @Mock
    private RecipeServiceV1 service;

    @Mock
    private RecipeToRecipeDTOMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeDTO();
        this.mockMvc = setUpMockMVC();
    }

    private static RecipeDTO getTestRecipeDTO() {
        final CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("Test categoryDTO")
                .build();
        final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                .name("Test measure unitDTO")
                .build();
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("Test ingredientDTO")
                .amount(1.0)
                .measureUnitDTO(measureUnitDTO)
                .build();
        return RecipeDTO.builder()
                .name("Test recipeDTO")
                .description("Test description")
                .difficultyDTO(DifficultyDTO.EASY)
                .categoryDTOs(Set.of(categoryDTO))
                .ingredientDTOs(Set.of(ingredientDTO))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions")
                .build();
    }

    private MockMvc setUpMockMVC() {
        final RecipeRestControllerV1 controller = new RecipeRestControllerV1(service, mapper);
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        return MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    @DisplayName("createRecipe method should return saved recipe in DTO format when request body is valid")
    void createRecipe_whenRequestBodyIsValid_returnsSavedRecipeDTO() throws Exception {
        when(service.createRecipe(any(RecipeDTO.class)))
                .thenReturn(Recipe.builder().id(1L).build());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeDTO.builder().id(1L).build());
        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(source)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("createRecipe method should return HTTP BadRequest status code when request body is invalid")
    void createRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new RecipeDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("createRecipe method should return HTTP BadRequest status code when request body is null")
    void createRecipe_whenRequestBodyIsNull_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getRecipe method should return recipe in DTO format when id exists")
    void getRecipe_whenIdExists_returnsRecipeDTOWithThatId() throws Exception {
        when(service.getRecipe(any(Long.class)))
                .thenReturn(Recipe.builder().id(1L).build());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeDTO.builder().id(1L).build());
        mockMvc.perform(get("/api/v1/recipes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("getRecipe method should return HTTP Not Found status code when id does not exist")
    void getRecipe_whenIdDoesNotExist_returnsHttpNotFound() throws Exception {
        when(service.getRecipe(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        final Long id = Long.MAX_VALUE;
        mockMvc.perform(get("/api/v1/recipes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getRecipe method should return HTTP BadRequest status code when id is not a number")
    void getRecipe_whenIdIsNotNumber_returnsHttpBadRequest() throws Exception {
        final String id = "notANumber";
        mockMvc.perform(get("/api/v1/recipes/{id}", id))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getRecipes method should return list of RecipeDTO objects.")
    void getRecipes_returnsListOfRecipeDTO() throws Exception {
        when(service.getRecipes())
                .thenReturn(List.of(Recipe.builder().id(1L).build()));
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeDTO.builder().id(1L).build());
        mockMvc.perform(get("/api/v1/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("updateRecipe method should return updated recipe in DTO format when request body is valid")
    void updateRecipe_whenRequestBodyIsValid_returnsUpdatedRecipeDTO() throws Exception {
        when(service.updateRecipe(any(RecipeDTO.class)))
                .thenReturn(Recipe.builder().id(1L).build());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeDTO.builder().id(1L).build());
        mockMvc.perform(put("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(source)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("updateRecipe method should return HTTP BadRequest status code when request body is invalid")
    void updateRecipe_whenRequestBodyIsInvalid_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new RecipeDTO())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("updateRecipe method should return HTTP BadRequest status code when request body is not present")
    void updateRecipe_whenRequestBodyIsNotPresent_returnsHttpBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("deleteRecipe method should return deleted recipe in DTO format when id is valid")
    void deleteRecipe_whenIdIsValid_returnsDeletedRecipeDTO() throws Exception {
        when(service.deleteRecipe(any(Long.class)))
                .thenReturn(Recipe.builder().id(1L).build());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(RecipeDTO.builder().id(1L).build());
        mockMvc.perform(delete("/api/v1/recipes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP Not Found status code when id is invalid")
    void deleteRecipe_whenIdIsInvalid_returnsHttpNotFound() throws Exception {
        when(service.deleteRecipe(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        final Long id = Long.MAX_VALUE;
        mockMvc.perform(delete("/api/v1/recipes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("deleteRecipe method should return HTTP BadRequest status code when id is not a number")
    void deleteRecipe_whenIsIsNotNumber_returnsHttpBadRequest() throws Exception {
        final String id = "notANumber";
        mockMvc.perform(delete("/api/v1/recipes/{id}", id))
                .andExpect(status().isBadRequest());
    }
}