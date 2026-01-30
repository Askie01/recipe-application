package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.mapper.IngredientDTOToIngredientMapper;
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

@SpringJUnitConfig(classes = IngredientDTOToIngredientMapperConfiguration.class)
@Import(value = {
        LongIdMapperConfiguration.class,
        StringNameMapperConfiguration.class,
        StringNameValidatorConfiguration.class,
        AmountMapperConfiguration.class,
        AmountValidatorConfiguration.class,
        MeasureUnitDTOToMeasureUnitMapperConfiguration.class,
        LongVersionMapperConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.ingredientDTO-to-ingredient-type=simple",
        "component.mapper.id-type=simple-long-id",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount",
        "component.mapper.measureUnitDTO-to-measureUnit-type=simple",
        "component.mapper.version-type=simple-long-version"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleIngredientDTOToIngredientMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleIngredientDTOToIngredientMapperIntegrationTest {

    private IngredientDTO source;
    private Ingredient target;
    private final IngredientDTOToIngredientMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestIngredientDTO();
        this.target = getTestIngredient();
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
    @DisplayName("map method should map source measureUnitDTO to target measureUnit when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitDTOToTargetMeasureUnit() {
        mapper.map(source, target);
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();

        final Long measureUnitDTOId = measureUnitDTO.getId();
        final Long measureUnitId = measureUnit.getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = measureUnitDTO.getName();
        final String measureUnitName = measureUnit.getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);
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

        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();

        final Long measureUnitDTOId = measureUnitDTO.getId();
        final Long measureUnitId = measureUnit.getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = measureUnitDTO.getName();
        final String measureUnitName = measureUnit.getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);

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
    @DisplayName("mapToEntity method should map source id to new ingredient id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewIngredientIdAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long ingredientId = ingredient.getId();
        assertEquals(sourceId, ingredientId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new ingredient name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewIngredientNameAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String ingredientName = ingredient.getName();
        assertEquals(sourceName, ingredientName);
    }

    @Test
    @DisplayName("mapToEntity method should map source amount to new ingredient amount and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceAmountToNewIngredientAmountAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Double sourceAmount = source.getAmount();
        final Double ingredientAmount = ingredient.getAmount();
        assertEquals(sourceAmount, ingredientAmount);
    }

    @Test
    @DisplayName("mapToEntity method should map source measureUnitDTO to new ingredientMeasureUnit and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceMeasureUnitDTOToNewIngredientMeasureUnitAndReturnIt() {
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = mapper.mapToEntity(source).getMeasureUnit();

        final Long measureUnitDTOId = measureUnitDTO.getId();
        final Long measureUnitId = measureUnit.getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = measureUnitDTO.getName();
        final String measureUnitName = measureUnit.getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new ingredient version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewIngredientVersionAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long ingredientVersion = ingredient.getVersion();
        assertEquals(sourceVersion, ingredientVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new ingredient and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientAndReturnIt() {
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long ingredientId = ingredient.getId();
        assertEquals(sourceId, ingredientId);

        final String sourceName = source.getName();
        final String ingredientName = ingredient.getName();
        assertEquals(sourceName, ingredientName);

        final Double sourceAmount = source.getAmount();
        final Double ingredientAmount = ingredient.getAmount();
        assertEquals(sourceAmount, ingredientAmount);

        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = ingredient.getMeasureUnit();

        final Long measureUnitDTOId = measureUnitDTO.getId();
        final Long measureUnitId = measureUnit.getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = measureUnitDTO.getName();
        final String measureUnitName = measureUnit.getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);

        final Long sourceVersion = source.getVersion();
        final Long ingredientVersion = ingredient.getVersion();
        assertEquals(sourceVersion, ingredientVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}