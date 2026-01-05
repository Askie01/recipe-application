package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.RandomCategoryDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryTestFactory;
import com.askie01.recipeapplication.model.entity.Category;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryCategoryDTOValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class CategoryCategoryDTOValueTestComparatorUnitTest {

    private Category category;
    private CategoryDTO categoryDTO;

    @Mock
    private LongIdTestComparator longIdTestComparator;

    @Mock
    private StringNameTestComparator stringNameTestComparator;

    @Mock
    private LongVersionTestComparator longVersionTestComparator;
    private CategoryCategoryDTOTestComparator categoryCategoryDTOTestComparator;

    @BeforeEach
    void setUp() {
        this.categoryCategoryDTOTestComparator = new CategoryCategoryDTOValueTestComparator(
                longIdTestComparator,
                stringNameTestComparator,
                longVersionTestComparator);
        final Faker faker = new Faker();
        this.category = new RandomCategoryTestFactory(faker).createCategory();
        this.categoryDTO = new RandomCategoryDTOTestFactory(faker).createCategoryDTO();
    }

    @Test
    @DisplayName("compare method should return true when category and categoryDTO have the same common field values")
    void compare_whenCategoryHaveTheSameCommonFieldValuesAsCategoryDTO_returnsTrue() {
        when(longIdTestComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(true);
        when(stringNameTestComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(true);
        when(longVersionTestComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(true);
        final boolean result = categoryCategoryDTOTestComparator.compare(category, categoryDTO);
        assertTrue(result);
    }

    @ParameterizedTest(name = "compare method should return false when Category and CategoryDTO have one common field with different value")
    @MethodSource("booleanTriples")
    void compare_whenCategoryAndCategoryDTOHaveOneCommonFieldWithDifferentValue_returnsFalse(boolean idEqual, boolean nameEqual, boolean versionEqual) {
        when(longIdTestComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(idEqual);
        when(stringNameTestComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(nameEqual);
        when(longVersionTestComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(versionEqual);
        final boolean result = categoryCategoryDTOTestComparator.compare(category, categoryDTO);
        assertFalse(result);
    }

    private static Stream<Arguments> booleanTriples() {
        return Stream.of(
                Arguments.of(false, true, true),
                Arguments.of(true, false, true),
                Arguments.of(true, true, false)
        );
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when category is null")
    void compare_whenCategoryIsNull_throwsNullPointerException() {
        when(longIdTestComparator
                .compare(eq(null), any(CategoryDTO.class)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> categoryCategoryDTOTestComparator.compare(null, categoryDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when categoryDTO is null")
    void compare_whenCategoryDTOIsNull_throwsNullPointerException() {
        when(longIdTestComparator
                .compare(any(Category.class), eq(null)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> categoryCategoryDTOTestComparator.compare(category, null));
    }
}