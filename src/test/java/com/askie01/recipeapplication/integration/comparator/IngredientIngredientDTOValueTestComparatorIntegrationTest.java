package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.comparator.IngredientIngredientDTOTestComparator;
import com.askie01.recipeapplication.configuration.IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomIngredientDTOTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomIngredientTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.SimpleMeasureUnitDTOToMeasureUnitMapperDefaultTestConfiguration;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOTestFactory;
import com.askie01.recipeapplication.factory.IngredientTestFactory;
import com.askie01.recipeapplication.mapper.MeasureUnitDTOToMeasureUnitMapper;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration.class,
        RandomIngredientTestFactoryDefaultTestConfiguration.class,
        RandomIngredientDTOTestFactoryDefaultTestConfiguration.class,
        SimpleMeasureUnitDTOToMeasureUnitMapperDefaultTestConfiguration.class,
})
@TestPropertySource(locations = "classpath:ingredient-ingredientDTO-value-test-comparator-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("IngredientIngredientDTOValueTestComparator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class IngredientIngredientDTOValueTestComparatorIntegrationTest {

    private Ingredient ingredient;
    private IngredientDTO ingredientDTO;
    private final IngredientTestFactory ingredientTestFactory;
    private final IngredientDTOTestFactory ingredientDTOTestFactory;
    private final MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper;
    private final IngredientIngredientDTOTestComparator ingredientIngredientDTOTestComparator;

    @BeforeEach
    void setUp() {
        this.ingredient = ingredientTestFactory.createIngredient();
        this.ingredientDTO = ingredientDTOTestFactory.createIngredientDTO();
    }

    @Test
    @DisplayName("compare method should return true when ingredient and ingredientDTO have the same common field values")
    void compare_whenIngredientHaveTheSameCommonFieldValuesAsIngredientDTO_returnsTrue() {
        final Long ingredientDTOId = ingredientDTO.getId();
        final String ingredientDTOName = ingredientDTO.getName();
        final Double ingredientDTOAmount = ingredientDTO.getAmount();
        final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
        final Long ingredientDTOVersion = ingredientDTO.getVersion();

        ingredient.setId(ingredientDTOId);
        ingredient.setName(ingredientDTOName);
        ingredient.setAmount(ingredientDTOAmount);
        final MeasureUnit measureUnit = measureUnitDTOToMeasureUnitMapper.mapToEntity(measureUnitDTO);
        ingredient.setMeasureUnit(measureUnit);
        ingredient.setVersion(ingredientDTOVersion);

        final boolean result = ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when Ingredient and IngredientDTO have different common field values")
    void compare_whenIngredientAndIngredientDTOHaveOneCommonFieldWithDifferentValue_returnsFalse() {
        final boolean result = ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when ingredient is null")
    void compare_whenIngredientIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(null, ingredientDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnit in Ingredient is null")
    void compare_whenMeasureUnitInIngredientIsNull_throwsNullPointerException() {
        ingredient.setMeasureUnit(null);
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when ingredientDTO is null")
    void compare_whenIngredientDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(ingredient, null));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnitDTO in IngredientDTO is null")
    void compare_whenMeasureUnitDTOInIngredientDTOIsNull_throwsNullPointerException() {
        ingredientDTO.setMeasureUnitDTO(null);
        assertThrows(NullPointerException.class, () -> ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO));
    }
}