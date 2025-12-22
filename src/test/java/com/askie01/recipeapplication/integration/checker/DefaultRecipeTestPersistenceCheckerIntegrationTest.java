package com.askie01.recipeapplication.integration.checker;

import com.askie01.recipeapplication.checker.RecipeTestPersistenceChecker;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.factory.RecipeTestFactory;
import com.askie01.recipeapplication.model.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        FakerTestConfiguration.class,
        RandomDifficultyTestFactoryDefaultTestConfiguration.class,
        RandomCategoryTestFactoryDefaultTestConfiguration.class,
        RandomIngredientTestFactoryTestConfiguration.class,
        RandomMeasureUnitTestFactoryDefaultTestConfiguration.class,
        RandomRecipeTestFactoryTestConfiguration.class,
        DefaultRecipeTestPersistenceCheckerDefaultTestConfiguration.class,
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeTestPersistenceChecker integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeTestPersistenceCheckerIntegrationTest {

    private Recipe source;
    private final RecipeTestPersistenceChecker checker;
    private final RecipeTestFactory factory;

    @BeforeEach
    void setUp() {
        this.source = factory.createRecipe();
    }

    @Test
    @DisplayName("wasCreated method should return true when source has updatedAt and updatedBy fields are null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsTrue() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        final boolean result = checker.wasCreated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source has updatedAt and updatedBy fields are not null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsFalse() {
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source categories were not created.")
    void wasCreated_whenSourceCategoriesWereNotCreated_returnsFalse() {
        source.getCategories().stream().findAny().get().setCreatedAt(null);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source ingredients were not created.")
    void wasCreated_whenSourceIngredientsWereNotCreated_returnsFalse() {
        source.getIngredients().stream().findAny().get().setCreatedAt(null);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should throw NullPointerException when source is null.")
    void wasCreated_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.wasCreated(null));
    }

    @Test
    @DisplayName("wasUpdated method should return true when source has updatedAt and updatedBy fields are not null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsTrue() {
        final boolean result = checker.wasUpdated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source has updatedAt and updatedBy fields are null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsFalse() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source categories were not created.")
    void wasUpdated_whenSourceCategoriesWereNotCreated_returnsFalse() {
        source.getCategories().stream().findAny().get().setCreatedAt(null);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source ingredients were not created.")
    void wasUpdated_whenSourceIngredientsWereNotCreated_returnsFalse() {
        source.getIngredients().stream().findAny().get().setCreatedAt(null);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should throw NullPointerException when source is null.")
    void wasUpdated_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.wasUpdated(null));
    }
}