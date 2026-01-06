package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOTestFactory;
import com.askie01.recipeapplication.factory.IngredientTestFactory;
import com.askie01.recipeapplication.mapper.IngredientDTOToIngredientMapper;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleIngredientDTOToIngredientMapperDefaultTestConfiguration.class,
        RandomIngredientDTOTestFactoryDefaultTestConfiguration.class,
        RandomIngredientTestFactoryDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorDefaultTestConfiguration.class,
        IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration.class
})
@TestPropertySource(locations = "classpath:simple-ingredientDTO-to-ingredient-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleIngredientDTOToIngredientMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleIngredientDTOToIngredientMapperIntegrationTest {

    private IngredientDTO source;
    private Ingredient target;
    private final IngredientTestFactory ingredientFactory;
    private final IngredientDTOTestFactory ingredientDTOFactory;
    private final IngredientDTOToIngredientMapper mapper;
    private final LongIdTestComparator idComparator;
    private final StringNameTestComparator nameComparator;
    private final AmountTestComparator amountComparator;
    private final LongVersionTestComparator versionComparator;
    private final MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;
    private final IngredientIngredientDTOTestComparator ingredientComparator;

    @BeforeEach
    void setUp() {
        this.source = ingredientDTOFactory.createIngredientDTO();
        this.target = ingredientFactory.createIngredient();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is present")
    void map_whenSourceIsPresent_mapsSourceAmountToTargetAmount() {
        mapper.map(source, target);
        final boolean equalAmount = amountComparator.compare(source, target);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("map method should map source measureUnitDTO to target measureUnit when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitDTOToTargetMeasureUnit() {
        mapper.map(source, target);
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();
        final boolean equalMeasureUnit = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnit);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final boolean equalIngredients = ingredientComparator.compare(target, source);
        assertTrue(equalIngredients);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new ingredient id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewIngredientIdAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, ingredient);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new ingredient name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewIngredientNameAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, ingredient);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source amount to new ingredient amount and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceAmountToNewIngredientAmountAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalAmount = amountComparator.compare(source, ingredient);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("mapToEntity method should map source measureUnitDTO to new ingredientMeasureUnit and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceMeasureUnitDTOToNewIngredientMeasureUnitAndReturnIt() {
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = mapper.mapToEntity(source).getMeasureUnit();
        final boolean equalMeasureUnits = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new ingredient version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewIngredientVersionAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, ingredient);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new ingredient and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalIngredients = ingredientComparator.compare(ingredient, source);
        assertTrue(equalIngredients);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}