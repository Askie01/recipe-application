package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.value.HasAmount;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.model.value.HasStringName;
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
@DisplayName("SimpleIngredientDTOToIngredientMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleIngredientDTOToIngredientMapperUnitTest {

    private IngredientDTO source;
    private Ingredient target;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private AmountMapper amountMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @Mock
    private MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper;
    private IngredientDTOToIngredientMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestIngredientDTO();
        this.target = getTestIngredient();
        this.mapper = new SimpleIngredientDTOToIngredientMapper(
                idMapper,
                nameMapper,
                amountMapper,
                measureUnitDTOToMeasureUnitMapper,
                versionMapper);
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
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is present")
    void map_whenSourceIsPresent_mapsSourceAmountToTargetAmount() {
        doAnswer(invocation -> {
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );
        mapper.map(source, target);
        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        assertEquals(sourceAmount, targetAmount);
    }

    @Test
    @DisplayName("map method should map source measureUnitDTO to target measureUnit when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitDTOToTargetMeasureUnit() {
        doAnswer(invocation -> {
            final MeasureUnitDTO source = invocation.getArgument(0);
            final MeasureUnit target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            final String sourceName = source.getName();
            final Long sourceVersion = source.getVersion();
            target.setId(sourceId);
            target.setName(sourceName);
            target.setVersion(sourceVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class)
        );

        mapper.map(source, target);
        final Long measureUnitDTOId = source.getMeasureUnitDTO().getId();
        final Long measureUnitId = target.getMeasureUnit().getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = source.getMeasureUnitDTO().getName();
        final String measureUnitName = target.getMeasureUnit().getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = source.getMeasureUnitDTO().getVersion();
        final Long measureUnitVersion = target.getMeasureUnit().getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        doAnswer(invocation -> {
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );
        doAnswer(invocation -> {
            final MeasureUnitDTO source = invocation.getArgument(0);
            final MeasureUnit target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            final String sourceName = source.getName();
            final Long sourceVersion = source.getVersion();
            target.setId(sourceId);
            target.setName(sourceName);
            target.setVersion(sourceVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class)
        );
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
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

        final Long measureUnitDTOId = source.getMeasureUnitDTO().getId();
        final Long measureUnitId = target.getMeasureUnit().getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = source.getMeasureUnitDTO().getName();
        final String measureUnitName = target.getMeasureUnit().getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Ingredient.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(IngredientDTO.class), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new ingredient id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewIngredientIdAndReturnIt() {
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long ingredientId = ingredient.getId();
        assertEquals(sourceId, ingredientId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new ingredient name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewIngredientNameAndReturnIt() {
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        final Ingredient ingredient = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String ingredientName = ingredient.getName();
        assertEquals(sourceName, ingredientName);
    }

    @Test
    @DisplayName("mapToEntity method should map source amount to new ingredient amount and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceAmountToNewIngredientAmountAndReturnIt() {
        doAnswer(invocation -> {
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Double sourceAmount = source.getAmount();
        final Double ingredientAmount = ingredient.getAmount();
        assertEquals(sourceAmount, ingredientAmount);
    }

    @Test
    @DisplayName("mapToEntity method should map source measureUnitDTO to new ingredientMeasureUnit and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceMeasureUnitDTOToNewIngredientMeasureUnitAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO source = invocation.getArgument(0);
            final MeasureUnit target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            final String sourceName = source.getName();
            final Long sourceVersion = source.getVersion();
            target.setId(sourceId);
            target.setName(sourceName);
            target.setVersion(sourceVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class)
        );

        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long measureUnitDTOId = source.getMeasureUnitDTO().getId();
        final Long measureUnitId = ingredient.getMeasureUnit().getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = source.getMeasureUnitDTO().getName();
        final String measureUnitName = ingredient.getMeasureUnit().getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = source.getMeasureUnitDTO().getVersion();
        final Long measureUnitVersion = ingredient.getMeasureUnit().getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new ingredient version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewIngredientVersionAndReturnIt() {
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        final Ingredient ingredient = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long ingredientVersion = ingredient.getVersion();
        assertEquals(sourceVersion, ingredientVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new ingredient and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientAndReturnIt() {
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        doAnswer(invocation -> {
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );
        doAnswer(invocation -> {
            final MeasureUnitDTO source = invocation.getArgument(0);
            final MeasureUnit target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            final String sourceName = source.getName();
            final Long sourceVersion = source.getVersion();
            target.setId(sourceId);
            target.setName(sourceName);
            target.setVersion(sourceVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class)
        );
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );

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

        final Long measureUnitDTOId = source.getMeasureUnitDTO().getId();
        final Long measureUnitId = ingredient.getMeasureUnit().getId();
        assertEquals(measureUnitDTOId, measureUnitId);

        final String measureUnitDTOName = source.getMeasureUnitDTO().getName();
        final String measureUnitName = ingredient.getMeasureUnit().getName();
        assertEquals(measureUnitDTOName, measureUnitName);

        final Long measureUnitDTOVersion = source.getMeasureUnitDTO().getVersion();
        final Long measureUnitVersion = ingredient.getMeasureUnit().getVersion();
        assertEquals(measureUnitDTOVersion, measureUnitVersion);

        final Long sourceVersion = source.getVersion();
        final Long ingredientVersion = ingredient.getVersion();
        assertEquals(sourceVersion, ingredientVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Ingredient.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}