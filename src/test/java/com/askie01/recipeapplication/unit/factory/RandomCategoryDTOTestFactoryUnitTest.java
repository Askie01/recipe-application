package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryDTOTestFactory;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomCategoryDTOTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomCategoryDTOTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;
    private CategoryDTOTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomCategoryDTOTestFactory(faker);
    }

    @Test
    @DisplayName("createCategoryDTO method should return random CategoryDTO object")
    void createCategoryDTO_shouldReturnRandomCategoryDTO() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), eq(false))).thenReturn(0L);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple category name");

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