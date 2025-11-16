package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryDTOUnsavedEntityTestFactory;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomCategoryDTOUnsavedEntityTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomCategoryDTOUnsavedEntityTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private FunnyName fakerFunnyName;
    private CategoryDTOUnsavedEntityTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomCategoryDTOUnsavedEntityTestFactory(faker);
    }

    @Test
    @DisplayName("createCategoryDTO method should return random CategoryDTO entity-like object")
    void createCategoryDTO_shouldReturnRandomCategoryDTOUnsavedEntity() {
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple category name");

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