package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.RandomIngredientDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
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
@DisplayName("IngredientIngredientDTOValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class IngredientIngredientDTOValueTestComparatorUnitTest {

    private Ingredient ingredient;
    private IngredientDTO ingredientDTO;

    @Mock
    private LongIdTestComparator longIdTestComparator;

    @Mock
    private StringNameTestComparator stringNameTestComparator;

    @Mock
    private AmountTestComparator amountTestComparator;

    @Mock
    private MeasureUnitMeasureUnitDTOTestComparator measureUnitMeasureUnitDTOTestComparator;

    @Mock
    private LongVersionTestComparator longVersionTestComparator;
    private IngredientIngredientDTOTestComparator ingredientIngredientDTOTestComparator;

    @BeforeEach
    void setUp() {
        this.ingredientIngredientDTOTestComparator = new IngredientIngredientDTOValueTestComparator(
                longIdTestComparator,
                stringNameTestComparator,
                amountTestComparator,
                measureUnitMeasureUnitDTOTestComparator,
                longVersionTestComparator);
        final Faker faker = new Faker();
        final RandomMeasureUnitTestFactory measureUnitTestFactory = new RandomMeasureUnitTestFactory(faker);
        final RandomMeasureUnitDTOTestFactory measureUnitDTOTestFactory = new RandomMeasureUnitDTOTestFactory(faker);
        this.ingredient = new RandomIngredientTestFactory(faker, measureUnitTestFactory).createIngredient();
        this.ingredientDTO = new RandomIngredientDTOTestFactory(faker, measureUnitDTOTestFactory).createIngredientDTO();
    }

    @Test
    @DisplayName("compare method should return true when ingredient and ingredientDTO have the same common field values")
    void compare_whenIngredientHaveTheSameCommonFieldValuesAsIngredientDTO_returnsTrue() {
        when(longIdTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(true);
        when(stringNameTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(true);
        when(amountTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(true);
        when(measureUnitMeasureUnitDTOTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(true);
        when(longVersionTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(true);
        final boolean result = ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO);
        assertTrue(result);
    }

    @ParameterizedTest(name = "compare method should return false when Ingredient and IngredientDTO have one common field with different value")
    @MethodSource("fiveBooleans")
    void compare_whenIngredientAndIngredientDTOHaveOneCommonFieldWithDifferentValue_returnsFalse(boolean idEqual,
                                                                                                 boolean nameEqual,
                                                                                                 boolean amountEqual,
                                                                                                 boolean measureUnitEqual,
                                                                                                 boolean versionEqual) {
        when(longIdTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(idEqual);
        when(stringNameTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(nameEqual);
        when(amountTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(amountEqual);
        when(measureUnitMeasureUnitDTOTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(measureUnitEqual);
        when(longVersionTestComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(versionEqual);
        final boolean result = ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO);
        assertFalse(result);
    }

    private static Stream<Arguments> fiveBooleans() {
        return Stream.of(
                Arguments.of(false, true, true, true, true),
                Arguments.of(true, false, true, true, true),
                Arguments.of(true, true, false, true, true),
                Arguments.of(true, true, true, false, true),
                Arguments.of(true, true, true, true, false)
        );
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when ingredient is null")
    void compare_whenIngredientIsNull_throwsNullPointerException() {
        when(longIdTestComparator
                .compare(eq(null), any(IngredientDTO.class)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(null, ingredientDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnit in Ingredient is null")
    void compare_whenMeasureUnitInIngredientIsNull_throwsNullPointerException() {
        when(measureUnitMeasureUnitDTOTestComparator
                .compare(eq(null), any(MeasureUnitDTO.class)))
                .thenThrow(NullPointerException.class);
        ingredient.setMeasureUnit(null);
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when ingredientDTO is null")
    void compare_whenIngredientDTOIsNull_throwsNullPointerException() {
        when(longIdTestComparator
                .compare(any(Ingredient.class), eq(null)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(ingredient, null));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnitDTO in IngredientDTO is null")
    void compare_whenMeasureUnitDTOInIngredientDTOIsNull_throwsNullPointerException() {
        when(measureUnitMeasureUnitDTOTestComparator
                .compare(any(MeasureUnit.class), eq(null)))
                .thenThrow(NullPointerException.class);
        ingredientDTO.setMeasureUnitDTO(null);
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO));
    }
}