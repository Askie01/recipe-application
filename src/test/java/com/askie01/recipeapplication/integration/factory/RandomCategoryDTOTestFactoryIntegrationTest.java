package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomCategoryDTOTestFactoryTestConfiguration;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RandomCategoryDTOTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomCategoryDTOTestFactory integration tests")
class RandomCategoryDTOTestFactoryIntegrationTest {

    private final CategoryDTOTestFactory factory;

    @Test
    @DisplayName("createCategoryDTO method should return random CategoryDTO object")
    void createCategoryDTO_shouldReturnRandomCategoryDTO() {
        final CategoryDTO categoryDTO = factory.createCategoryDTO();
        final Long categoryId = categoryDTO.getId();
        final String categoryName = categoryDTO.getName();
        final Long categoryVersion = categoryDTO.getVersion();

        assertNotNull(categoryDTO);
        assertNotNull(categoryId);
        assertNotNull(categoryName);
        assertNotNull(categoryVersion);
    }
}