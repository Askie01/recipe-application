package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.comparator.CategoryCategoryDTOTestComparator;
import com.askie01.recipeapplication.configuration.CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomCategoryDTOTestFactoryTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomCategoryTestFactoryTestConfiguration;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOTestFactory;
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
        FakerTestConfiguration.class,
        RandomCategoryDTOTestFactoryTestConfiguration.class,
        RandomCategoryTestFactoryTestConfiguration.class,
        CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("CategoryCategoryDTOValueTestComparator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class CategoryCategoryDTOValueTestComparatorIntegrationTest {

    private final CategoryCategoryDTOTestComparator comparator;
    private final CategoryTestFactory categoryFactory;
    private final CategoryDTOTestFactory categoryDTOFactory;
    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        this.category = categoryFactory.createCategory();
        this.categoryDTO = categoryDTOFactory.createCategoryDTO();
    }

    @Test
    @DisplayName("compare method should return true when category and categoryDTO have the same common field values")
    void compare_whenCategoryHaveTheSameCommonFieldValuesAsCategoryDTO_returnsTrue() {
        final Long categoryId = category.getId();
        final String categoryName = category.getName();
        final Long categoryVersion = category.getVersion();
        categoryDTO.setId(categoryId);
        categoryDTO.setName(categoryName);
        categoryDTO.setVersion(categoryVersion);
        final boolean result = comparator.compare(category, categoryDTO);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when category and categoryDTO have different common field values")
    void compare_whenCategoryHaveDifferentCommonFieldValuesFromCategoryDTO_returnsFalse() {
        final boolean result = comparator.compare(category, categoryDTO);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when category is null")
    void compare_whenCategoryIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, categoryDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when categoryDTO is null")
    void compare_whenCategoryDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(category, null));
    }
}