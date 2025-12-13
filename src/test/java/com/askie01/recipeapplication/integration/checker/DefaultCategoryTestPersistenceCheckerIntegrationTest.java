package com.askie01.recipeapplication.integration.checker;

import com.askie01.recipeapplication.checker.CategoryTestPersistenceChecker;
import com.askie01.recipeapplication.configuration.DefaultCategoryTestPersistenceCheckerDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomCategoryTestFactoryTestConfiguration;
import com.askie01.recipeapplication.factory.CategoryTestFactory;
import com.askie01.recipeapplication.model.entity.Category;
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
        DefaultCategoryTestPersistenceCheckerDefaultTestConfiguration.class,
        RandomCategoryTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultCategoryTestPersistenceChecker integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultCategoryTestPersistenceCheckerIntegrationTest {

    private Category source;
    private final CategoryTestFactory factory;
    private final CategoryTestPersistenceChecker checker;

    @BeforeEach
    void setUp() {
        this.source = factory.createCategory();
    }

    @Test
    @DisplayName("wasCreated method should return true when source has updatedAt and updatedBy fields null.")
    void wasCreated_whenSourceHasUpdateAtAndUpdatedByNull_returnsTrue() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        final boolean result = checker.wasCreated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source has updatedAt and updatedBy fields not null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsFalse() {
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should throw NullPointerException when source is null.")
    void wasCreated_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.wasCreated(null));
    }

    @Test
    @DisplayName("wasUpdated method should return true when source has updatedAt and updatedBy fields not null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsTrue() {
        final boolean result = checker.wasUpdated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source has updatedAt and updatedBy fields null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsFalse() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should throw NullPointerException when source is null.")
    void wasUpdated_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.wasUpdated(null));
    }
}