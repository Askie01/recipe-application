package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomCategoryTestFactoryTestConfiguration;
import com.askie01.recipeapplication.factory.CategoryTestFactory;
import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RandomCategoryTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomCategoryTestFactory integration tests")
class RandomCategoryTestFactoryIntegrationTest {

    private final CategoryTestFactory factory;

    @Test
    @DisplayName("createCategory method should return random Category object")
    void createCategory_shouldReturnRandomCategory() {
        final Category category = factory.createCategory();
        final Long categoryId = category.getId();
        final String categoryName = category.getName();
        final LocalDateTime categoryCreatedAt = category.getCreatedAt();
        final String categoryCreatedBy = category.getCreatedBy();
        final LocalDateTime categoryUpdatedAt = category.getUpdatedAt();
        final String categoryUpdatedBy = category.getUpdatedBy();
        final Long categoryVersion = category.getVersion();

        assertNotNull(category);
        assertNotNull(categoryId);
        assertNotNull(categoryName);
        assertNotNull(categoryCreatedAt);
        assertNotNull(categoryCreatedBy);
        assertNotNull(categoryUpdatedAt);
        assertNotNull(categoryUpdatedBy);
        assertNotNull(categoryVersion);
    }
}