package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.mapper.IngredientToIngredientDTOMapper;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = IngredientToIngredientDTOMapperConfiguration.class)
@Import(value = {
        LongIdMapperConfiguration.class,
        StringNameMapperConfiguration.class,
        StringNameValidatorConfiguration.class,
        AmountMapperConfiguration.class,
        AmountValidatorConfiguration.class,
        MeasureUnitToMeasureUnitDTOMapperConfiguration.class,
        LongVersionMapperConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.ingredient-to-ingredientDTO-type=simple",
        "component.mapper.id-type=simple-long-id",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount",
        "component.mapper.measureUnit-to-measureUnitDTO-type=simple",
        "component.mapper.version-type=simple-long-version"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleIngredientToIngredientDTOMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleIngredientToIngredientDTOMapperIntegrationTest {

    private Ingredient source;
    private IngredientDTO target;
    private final IngredientToIngredientDTOMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestIngredient();
        this.target = getTestIngredientDTO();
    }

    private static Ingredient getTestIngredient() {
        final MeasureUnit measureUnit = MeasureUnit.builder()
                .id(2L)
                .name("Test measure unit")
                .version(2L)
                .build();
        return Ingredient.builder()
                .id(2L)
                .name("Test ingredient")
                .amount(2.0)
                .measureUnit(measureUnit)
                .version(2L)
                .build();
    }

    private static IngredientDTO getTestIngredientDTO() {
        final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                .id(1L)
                .name("Test measure unitDTO")
                .version(1L)
                .build();
        return IngredientDTO.builder()
                .id(1L)
                .name("Test ingredientDTO")
                .amount(1.0)
                .measureUnitDTO(measureUnitDTO)
                .version(1L)
                .build();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is present")
    void map_whenSourceIsPresent_mapsSourceAmountToTargetAmount() {
        mapper.map(source, target);
        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        assertEquals(sourceAmount, targetAmount);
    }

    @Test
    @DisplayName("map method should map source measureUnit to target measureUnitDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitToTargetMeasureUnitDTO() {
        mapper.map(source, target);
        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = target.getMeasureUnitDTO();

        final Long measureUnitId = measureUnit.getId();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = measureUnit.getName();
        final String measureUnitDTOName = measureUnitDTO.getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = measureUnit.getVersion();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        assertEquals(measureUnitVersion, measureUnitDTOVersion);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);

        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);

        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        assertEquals(sourceAmount, targetAmount);

        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = target.getMeasureUnitDTO();

        final Long measureUnitId = measureUnit.getId();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = measureUnit.getName();
        final String measureUnitDTOName = measureUnitDTO.getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = measureUnit.getVersion();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        assertEquals(measureUnitVersion, measureUnitDTOVersion);

        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
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
        final Long sourceId = source.getId();
        final Long ingredientDTOId = ingredientDTO.getId();
        assertEquals(sourceId, ingredientDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new ingredientDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewIngredientDTONameAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final String sourceName = source.getName();
        final String ingredientDTOName = ingredientDTO.getName();
        assertEquals(sourceName, ingredientDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source amount to new ingredientDTO amount and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceAmountToNewIngredientDTOAmountAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Double sourceAmount = source.getAmount();
        final Double ingredientDTOAmount = ingredientDTO.getAmount();
        assertEquals(sourceAmount, ingredientDTOAmount);
    }

    @Test
    @DisplayName("mapToDTO method should map source measureUnit to new ingredientDTOMeasureUnitDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceMeasureUnitToNewIngredientDTOMeasureUnitDTOAndReturnIt() {
        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source).getMeasureUnitDTO();
        final Long measureUnitId = measureUnit.getId();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        assertEquals(measureUnitId, measureUnitDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new ingredientDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewIngredientDTOVersionAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Long sourceVersion = source.getVersion();
        final Long ingredientDTOVersion = ingredientDTO.getVersion();
        assertEquals(sourceVersion, ingredientDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new ingredientDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientDTOAndReturnIt() {
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long ingredientDTOId = ingredientDTO.getId();
        assertEquals(sourceId, ingredientDTOId);

        final String sourceName = source.getName();
        final String ingredientDTOName = ingredientDTO.getName();
        assertEquals(sourceName, ingredientDTOName);

        final Double sourceAmount = source.getAmount();
        final Double ingredientDTOAmount = ingredientDTO.getAmount();
        assertEquals(sourceAmount, ingredientDTOAmount);

        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();

        final Long measureUnitId = measureUnit.getId();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = measureUnit.getName();
        final String measureUnitDTOName = measureUnitDTO.getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = measureUnit.getVersion();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        assertEquals(measureUnitVersion, measureUnitDTOVersion);

        final Long sourceVersion = source.getVersion();
        final Long ingredientDTOVersion = ingredientDTO.getVersion();
        assertEquals(sourceVersion, ingredientDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}