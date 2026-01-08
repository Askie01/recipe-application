package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOTestFactory;
import com.askie01.recipeapplication.factory.IngredientTestFactory;
import com.askie01.recipeapplication.mapper.IngredientToIngredientDTOMapper;
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
        SimpleIngredientToIngredientDTOMapperDefaultTestConfiguration.class,
        RandomIngredientDTOTestFactoryDefaultTestConfiguration.class,
        RandomIngredientTestFactoryDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorDefaultTestConfiguration.class,
        IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration.class
})
@TestPropertySource(locations = "classpath:simple-ingredient-to-ingredientDTO-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleIngredientToIngredientDTOMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleIngredientToIngredientDTOMapperIntegrationTest {

    private Ingredient source;
    private IngredientDTO target;
    private final IngredientTestFactory ingredientFactory;
    private final IngredientDTOTestFactory ingredientDTOFactory;
    private final IngredientToIngredientDTOMapper mapper;
    private final LongIdTestComparator idComparator;
    private final StringNameTestComparator nameComparator;
    private final AmountTestComparator amountComparator;
    private final LongVersionTestComparator versionComparator;
    private final MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;
    private final IngredientIngredientDTOTestComparator ingredientComparator;

    @BeforeEach
    void setUp() {
        this.source = ingredientFactory.createIngredient();
        this.target = ingredientDTOFactory.createIngredientDTO();
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
    @DisplayName("map method should map source measureUnit to target measureUnitDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitToTargetMeasureUnitDTO() {
        mapper.map(source, target);
        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = target.getMeasureUnitDTO();
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
        final boolean equalIngredients = ingredientComparator.compare(source, target);
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
    @DisplayName("mapToDTO method should map source id to new ingredientDTO id and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewIngredientDTOIdAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalId = idComparator.compare(ingredientDTO, source);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new ingredientDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewIngredientDTONameAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalName = nameComparator.compare(ingredientDTO, source);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToDTO method should map source amount to new ingredientDTO amount and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceAmountToNewIngredientDTOAmountAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalAmount = amountComparator.compare(ingredientDTO, source);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("mapToDTO method should map source measureUnit to new ingredientDTOMeasureUnitDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceMeasureUnitToNewIngredientDTOMeasureUnitDTOAndReturnIt() {
        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source).getMeasureUnitDTO();
        final boolean equalMeasureUnits = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new ingredientDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewIngredientDTOVersionAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalVersion = versionComparator.compare(ingredientDTO, source);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new ingredientDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientDTOAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalIngredients = ingredientComparator.compare(source, ingredientDTO);
        assertTrue(equalIngredients);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}