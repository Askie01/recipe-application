package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomCategoryDTOUnsavedEntityTestFactoryTestConfiguration;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOUnsavedEntityTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        FakerTestConfiguration.class,
        RandomCategoryDTOUnsavedEntityTestFactoryTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RandomCategoryDTOUnsavedEntityTestFactory integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RandomCategoryDTOUnsavedEntityTestFactoryIntegrationTest {

    private final CategoryDTOUnsavedEntityTestFactory factory;

    @Test
    @DisplayName("createCategoryDTO method should return random CategoryDTO entity-like object")
    void createCategoryDTO_shouldReturnRandomCategoryDTOUnsavedEntity() {
        final CategoryDTO categoryDTO = factory.createCategoryDTO();
        final Long categoryId = categoryDTO.getId();
        final String categoryName = categoryDTO.getName();
        final Long categoryVersion = categoryDTO.getVersion();

        assertNotNull(categoryDTO);
        assertNull(categoryId);
        assertNotNull(categoryName);
        assertNull(categoryVersion);
    }
}