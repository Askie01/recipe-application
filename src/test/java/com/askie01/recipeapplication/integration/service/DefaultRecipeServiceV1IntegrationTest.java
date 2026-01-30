package com.askie01.recipeapplication.integration.service;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.repository.RecipeRepository;
import com.askie01.recipeapplication.service.RecipeServiceV1;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(value = {
        JpaAuditingConfiguration.class,
        RecipeServiceV1Configuration.class,
        RecipeDTOToRecipeMapperConfiguration.class,
        LongIdMapperConfiguration.class,
        ImageMapperConfiguration.class,
        ImageValidatorConfiguration.class,
        StringNameMapperConfiguration.class,
        StringNameValidatorConfiguration.class,
        DescriptionMapperConfiguration.class,
        DescriptionValidatorConfiguration.class,
        DifficultyDTOToDifficultyMapperConfiguration.class,
        CategoryDTOToCategoryMapperConfiguration.class,
        LongVersionMapperConfiguration.class,
        IngredientDTOToIngredientMapperConfiguration.class,
        AmountMapperConfiguration.class,
        AmountValidatorConfiguration.class,
        MeasureUnitDTOToMeasureUnitMapperConfiguration.class,
        ServingsMapperConfiguration.class,
        ServingsValidatorConfiguration.class,
        CookingTimeMapperConfiguration.class,
        CookingTimeValidatorConfiguration.class,
        InstructionsMapperConfiguration.class,
        InstructionsValidatorConfiguration.class
})
@TestPropertySource(properties = {
        "component.auditor-type=recipe-service-auditor",
        "component.service.recipe=v1",
        "component.mapper.recipeDTO-to-recipe-type=simple",
        "component.mapper.id-type=simple-long-id",
        "component.mapper.image-type=validated-image",
        "component.validator.image-type=five-mega-bytes-image",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.description-type=validated-description",
        "component.validator.description-type=non-blank-description",
        "component.mapper.difficultyDTO-to-difficulty-type=simple",
        "component.mapper.categoryDTO-to-category-type=simple",
        "component.mapper.version-type=simple-long-version",
        "component.mapper.ingredientDTO-to-ingredient-type=simple",
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount",
        "component.mapper.measureUnitDTO-to-measureUnit-type=simple",
        "component.mapper.servings-type=validated-servings",
        "component.validator.servings-type=positive-servings",
        "component.mapper.cooking-time-type=validated-cooking-time",
        "component.validator.cooking-time-type=positive-cooking-time",
        "component.mapper.instructions-type=validated-instructions",
        "component.validator.instructions-type=non-blank-instructions"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeServiceV1 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeServiceV1IntegrationTest {

    private RecipeDTO source;
    private final RecipeServiceV1 service;
    private final EntityManager entityManager;
    private final RecipeRepository repository;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeDTO();
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
                .categoryDTOs(new HashSet<>(Set.of(categoryDTO)))
                .ingredientDTOs(new HashSet<>(Set.of(ingredientDTO)))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions")
                .build();
    }

    @Test
    @DisplayName("createRecipe method should return saved recipe when recipeDTO is new")
    void createRecipe_whenRecipeDTOIsNew_returnsSavedRecipe() {
        final Long recipeId = service.createRecipe(source).getId();
        entityManager.flush();
        entityManager.clear();
        final Recipe recipe = repository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
        final boolean recipeWasCreated = recipe.getId() != null;
        assertTrue(recipeWasCreated);
    }

    @Test
    @DisplayName("createRecipe method should throw IllegalArgumentException when recipe already exists")
    void createRecipe_whenRecipeDTOAlreadyExists_throwsIllegalArgumentException() {
        final Long randomRecipeId = repository.findAll().stream().findAny().orElseThrow().getId();
        source.setId(randomRecipeId);
        assertThrows(IllegalArgumentException.class, () -> service.createRecipe(source));
    }

    @Test
    @DisplayName("createRecipe method should throw NullPointerException when recipeDTO is null")
    void createRecipe_whenRecipeDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.createRecipe(null));
    }

    @Test
    @DisplayName("getRecipe method should return recipe when id exists")
    void getRecipe_whenIdExists_returnsRecipe() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        final Recipe recipe = service.getRecipe(id);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("getRecipe method should throw RecipeNotFoundException when id does not exist")
    void getRecipe_whenIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipe(id));
    }

    @Test
    @DisplayName("getRecipe method should throw InvalidDataAccessApiUsageException when id is null")
    void getRecipe_whenIdIsNull_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.getRecipe(null));
    }

    @Test
    @DisplayName("getRecipes method should return list of recipes")
    void getRecipes_returnsListOfRecipes() {
        final List<Recipe> recipes = service.getRecipes();
        assertNotNull(recipes);
    }

    @Test
    @DisplayName("updateRecipe method should return updated recipe when recipeDTO exists")
    void updateRecipe_whenRecipeDTOExists_returnsUpdatedRecipe() {
        final Recipe recipeToUpdate = repository.findAll().stream().findAny().orElseThrow();
        final Long id = recipeToUpdate.getId();
        final Long version = recipeToUpdate.getVersion();
        source.setId(id);
        service.updateRecipe(source);
        entityManager.flush();
        entityManager.clear();
        final Recipe updatedRecipe = repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        final boolean recipeWasUpdated = updatedRecipe.getVersion() == version + 1;
        assertTrue(recipeWasUpdated);
    }

    @Test
    @DisplayName("updateRecipe method should throw InvalidDataAccessApiUsageException when recipeDTO does not exist")
    void updateRecipe_whenRecipeDTODoesNotExist_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.updateRecipe(source));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when recipeDTO is null")
    void updateRecipe_whenRecipeDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.updateRecipe(null));
    }

    @Test
    @DisplayName("deleteRecipe method should return deleted recipe when id exists")
    void deleteRecipe_whenIdExists_returnsDeletedRecipe() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        final Recipe recipe = service.deleteRecipe(id);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("deleteRecipe method should throw RecipeNotFoundException when id does not exist")
    void deleteRecipe_whenIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.deleteRecipe(id));
    }

    @Test
    @DisplayName("deleteRecipe method should throw InvalidDataAccessApiUsageException when id is null")
    void deleteRecipe_whenIdIsNull_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.deleteRecipe(null));
    }
}