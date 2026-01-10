package com.askie01.recipeapplication.integration.service;

import com.askie01.recipeapplication.checker.RecipeTestPersistenceChecker;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.factory.RecipeDTOUnsavedEntityTestFactory;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(value = {
        EnableJpaAuditingConfiguration.class,
        RecipeServiceStringAuditorConfiguration.class,
        DefaultRecipeServiceV1DefaultTestConfiguration.class,
        RandomRecipeDTOUnsavedEntityTestFactoryDefaultTestConfiguration.class,
        DefaultRecipeTestPersistenceCheckerDefaultTestConfiguration.class
})
@TestPropertySource(locations = {
        "classpath:default-recipe-service-v1-default-test-configuration.properties",
        "classpath:h2-database-default-test-configuration.properties"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeServiceV1 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeServiceV1IntegrationTest {

    private RecipeDTO source;
    private final RecipeServiceV1 service;
    private final EntityManager entityManager;
    private final RecipeRepository repository;
    private final RecipeTestPersistenceChecker checker;
    private final RecipeDTOUnsavedEntityTestFactory factory;

    @BeforeEach
    void setUp() {
        this.source = factory.createRecipeDTO();
    }

    @Test
    @DisplayName("createRecipe method should return saved recipe when recipeDTO is new")
    void createRecipe_whenRecipeDTOIsNew_returnsSavedRecipe() {
        final Long recipeId = service.createRecipe(source).getId();
        entityManager.flush();
        entityManager.clear();
        final Recipe recipe = repository.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
        final boolean recipeWasCreated = checker.wasCreated(recipe);
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
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        source.setId(id);
        service.updateRecipe(source);
        entityManager.flush();
        entityManager.clear();
        final Recipe recipe = repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
        final boolean recipeWasUpdated = checker.wasUpdated(recipe);
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