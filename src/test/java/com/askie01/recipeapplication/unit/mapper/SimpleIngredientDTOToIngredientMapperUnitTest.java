package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("SimpleIngredientDTOToIngredientMapper unit tests")
class SimpleIngredientDTOToIngredientMapperUnitTest {

    @Mock
    private LongIdMapper longIdMapper;

    @Mock
    private StringNameMapper stringNameMapper;

    @Mock
    private AmountMapper amountMapper;

    @Mock
    private MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper;

    @Mock
    private LongVersionMapper longVersionMapper;
    private IngredientDTOToIngredientMapper mapper;
    private IngredientDTO source;
    private Ingredient target;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleIngredientDTOToIngredientMapper(
                longIdMapper,
                stringNameMapper,
                amountMapper,
                measureUnitDTOToMeasureUnitMapper,
                longVersionMapper);
        final Faker faker = new Faker();
        final MeasureUnitDTOTestFactory measureUnitDTOTestFactory = new RandomMeasureUnitDTOTestFactory(faker);
        final MeasureUnitTestFactory measureUnitTestFactory = new RandomMeasureUnitTestFactory(faker);
        this.source = new RandomIngredientDTOTestFactory(faker, measureUnitDTOTestFactory).createIngredientDTO();
        this.target = new RandomIngredientTestFactory(faker, measureUnitTestFactory).createIngredient();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(longIdMapper).map(source, target);
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(stringNameMapper).map(source, target);
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is present")
    void map_whenSourceIsPresent_mapsSourceAmountToTargetAmount() {
        doAnswer(invocation -> {
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(source, target);
        mapper.map(source, target);
        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        assertEquals(sourceAmount, targetAmount);
    }

    @Test
    @DisplayName("map method should map source measureUnitDTO to target measureUnit when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitDTOToTargetMeasureUnit() {
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();

        doAnswer(invocation -> {
            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper)
                .map(measureUnitDTO, measureUnit);

        mapper.map(source, target);

        final Long measureUnitDTOId = measureUnitDTO.getId();
        final String measureUnitDTOName = measureUnitDTO.getName();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();

        final Long measureUnitId = measureUnit.getId();
        final String measureUnitName = measureUnit.getName();
        final Long measureUnitVersion = measureUnit.getVersion();

        assertEquals(measureUnitDTOId, measureUnitId);
        assertEquals(measureUnitDTOName, measureUnitName);
        assertEquals(measureUnitDTOVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(longVersionMapper).map(source, target);
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(longIdMapper).map(source, target);

        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(stringNameMapper).map(source, target);

        doAnswer(invocation -> {
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(source, target);

        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();

        doAnswer(invocation -> {
            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper)
                .map(measureUnitDTO, measureUnit);

        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(longVersionMapper).map(source, target);

        mapper.map(source, target);

        final Long sourceId = source.getId();
        final String sourceName = source.getName();
        final Double sourceAmount = source.getAmount();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        final String measureUnitDTOName = measureUnitDTO.getName();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        final Long sourceVersion = source.getVersion();

        final Long targetId = target.getId();
        final String targetName = target.getName();
        final Double targetAmount = target.getAmount();
        final Long targetMeasureUnitId = measureUnit.getId();
        final String targetMeasureUnitName = measureUnit.getName();
        final Long targetMeasureUnitVersion = measureUnit.getVersion();
        final Long targetVersion = target.getVersion();

        assertEquals(sourceId, targetId);
        assertEquals(sourceName, targetName);
        assertEquals(sourceAmount, targetAmount);
        assertEquals(measureUnitDTOId, targetMeasureUnitId);
        assertEquals(measureUnitDTOName, targetMeasureUnitName);
        assertEquals(measureUnitDTOVersion, targetMeasureUnitVersion);
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(isNull(), any(Ingredient.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(any(IngredientDTO.class), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new ingredient id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewIngredientIdAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOId = ingredientDTO.getId();
            ingredient.setId(ingredientDTOId);
            return null;
        }).when(longIdMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long ingredientDTOId = source.getId();
        final Long ingredientId = ingredient.getId();
        assertEquals(ingredientDTOId, ingredientId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new ingredient name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewIngredientNameAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final String ingredientDTOName = ingredientDTO.getName();
            ingredient.setName(ingredientDTOName);
            return null;
        }).when(stringNameMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final String ingredientDTOName = source.getName();
        final String ingredientName = ingredient.getName();
        assertEquals(ingredientDTOName, ingredientName);
    }

    @Test
    @DisplayName("mapToEntity method should map source amount to new ingredient amount and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceAmountToNewIngredientAmountAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Double ingredientDTOAmount = ingredientDTO.getAmount();
            ingredient.setAmount(ingredientDTOAmount);
            return null;
        }).when(amountMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Double ingredientDTOAmount = source.getAmount();
        final Double ingredientAmount = ingredient.getAmount();
        assertEquals(ingredientDTOAmount, ingredientAmount);
    }

    @Test
    @DisplayName("mapToEntity method should map source measureUnitDTO to new ingredientMeasureUnit and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceMeasureUnitDTOToNewIngredientMeasureUnitAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();

            final MeasureUnit measureUnit = invocation.getArgument(1);
            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class));

        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        final String measureUnitDTOName = measureUnitDTO.getName();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();

        final MeasureUnit measureUnit = mapper.mapToEntity(source).getMeasureUnit();
        final Long measureUnitId = measureUnit.getId();
        final String measureUnitName = measureUnit.getName();
        final Long measureUnitVersion = measureUnit.getVersion();

        assertEquals(measureUnitDTOId, measureUnitId);
        assertEquals(measureUnitDTOName, measureUnitName);
        assertEquals(measureUnitDTOVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new ingredient version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewIngredientVersionAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOVersion = ingredientDTO.getVersion();
            ingredient.setVersion(ingredientDTOVersion);
            return null;
        }).when(longVersionMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long ingredientDTOVersion = source.getVersion();
        final Long ingredientVersion = ingredient.getVersion();
        assertEquals(ingredientDTOVersion, ingredientVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new ingredient and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOId = ingredientDTO.getId();
            ingredient.setId(ingredientDTOId);
            return null;
        }).when(longIdMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final String ingredientDTOName = ingredientDTO.getName();
            ingredient.setName(ingredientDTOName);
            return null;
        }).when(stringNameMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Double ingredientDTOAmount = ingredientDTO.getAmount();
            ingredient.setAmount(ingredientDTOAmount);
            return null;
        }).when(amountMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);

            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();

            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class));

        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOVersion = ingredientDTO.getVersion();
            ingredient.setVersion(ingredientDTOVersion);
            return null;
        }).when(longVersionMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        final Ingredient ingredient = mapper.mapToEntity(source);

        final Long sourceId = source.getId();
        final String sourceName = source.getName();
        final Double sourceAmount = source.getAmount();
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        final String measureUnitDTOName = measureUnitDTO.getName();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        final Long sourceVersion = source.getVersion();

        final Long ingredientId = ingredient.getId();
        final String ingredientName = ingredient.getName();
        final Double ingredientAmount = ingredient.getAmount();
        final MeasureUnit ingredientMeasureUnit = ingredient.getMeasureUnit();
        final Long ingredientMeasureUnitId = ingredientMeasureUnit.getId();
        final String ingredientMeasureUnitName = ingredientMeasureUnit.getName();
        final Long ingredientMeasureUnitVersion = ingredientMeasureUnit.getVersion();
        final Long ingredientVersion = ingredient.getVersion();

        assertEquals(sourceId, ingredientId);
        assertEquals(sourceName, ingredientName);
        assertEquals(sourceAmount, ingredientAmount);
        assertEquals(measureUnitDTOId, ingredientMeasureUnitId);
        assertEquals(measureUnitDTOName, ingredientMeasureUnitName);
        assertEquals(measureUnitDTOVersion, ingredientMeasureUnitVersion);
        assertEquals(sourceVersion, ingredientVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(isNull(), any(Ingredient.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}