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
@DisplayName("SimpleIngredientToIngredientDTOMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleIngredientToIngredientDTOMapperUnitTest {

    private Ingredient source;
    private IngredientDTO target;
    private IngredientToIngredientDTOMapper mapper;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private AmountMapper amountMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @Mock
    private MeasureUnitToMeasureUnitDTOMapper measureUnitToMeasureUnitDTOMapper;

    @BeforeEach
    void setUp() {
        this.source = getTestIngredient();
        this.target = getTestIngredientDTO();
        this.mapper = new SimpleIngredientToIngredientDTOMapper(
                idMapper,
                nameMapper,
                amountMapper,
                measureUnitToMeasureUnitDTOMapper,
                versionMapper);
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
    @DisplayName("map method should map source measureUnit to target measureUnitDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitToTargetMeasureUnitDTO() {
        doAnswer(invocation -> {
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceId = source.getId();
            final String sourceName = source.getName();
            final Long sourceVersion = source.getVersion();
            target.setId(sourceId);
            target.setName(sourceName);
            target.setVersion(sourceVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
        );
        mapper.map(source, target);
        final Long measureUnitId = source.getMeasureUnit().getId();
        final Long measureUnitDTOId = target.getMeasureUnitDTO().getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = source.getMeasureUnit().getName();
        final String measureUnitDTOName = target.getMeasureUnitDTO().getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = source.getMeasureUnit().getVersion();
        final Long measureUnitDTOVersion = target.getMeasureUnitDTO().getVersion();
        assertEquals(measureUnitVersion, measureUnitDTOVersion);
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
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceId = source.getId();
            final String sourceName = source.getName();
            final Long sourceVersion = source.getVersion();
            target.setId(sourceId);
            target.setName(sourceName);
            target.setVersion(sourceVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
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

        final Long measureUnitId = source.getMeasureUnit().getId();
        final Long measureUnitDTOId = target.getMeasureUnitDTO().getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = source.getMeasureUnit().getName();
        final String measureUnitDTOName = target.getMeasureUnitDTO().getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = source.getMeasureUnit().getVersion();
        final Long measureUnitDTOVersion = target.getMeasureUnitDTO().getVersion();
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long ingredientDTOId = ingredientDTO.getId();
        assertEquals(sourceId, ingredientDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new ingredientDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewIngredientDTONameAndReturnIt() {
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final String sourceName = source.getName();
        final String ingredientDTOName = ingredientDTO.getName();
        assertEquals(sourceName, ingredientDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source amount to new ingredientDTO amount and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceAmountToNewIngredientDTOAmountAndReturnIt() {
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Double sourceAmount = source.getAmount();
        final Double ingredientDTOAmount = ingredientDTO.getAmount();
        assertEquals(sourceAmount, ingredientDTOAmount);
    }

    @Test
    @DisplayName("mapToDTO method should map source measureUnit to new ingredientDTOMeasureUnitDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceMeasureUnitToNewIngredientDTOMeasureUnitDTOAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceMeasureUnitId = source.getId();
            final String sourceMeasureUnitName = source.getName();
            final Long sourceMeasureUnitVersion = source.getVersion();
            target.setId(sourceMeasureUnitId);
            target.setName(sourceMeasureUnitName);
            target.setVersion(sourceMeasureUnitVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
        );

        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Long measureUnitId = source.getMeasureUnit().getId();
        final Long measureUnitDTOId = ingredientDTO.getMeasureUnitDTO().getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = source.getMeasureUnit().getName();
        final String measureUnitDTOName = ingredientDTO.getMeasureUnitDTO().getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = source.getMeasureUnit().getVersion();
        final Long measureUnitDTOVersion = ingredientDTO.getMeasureUnitDTO().getVersion();
        assertEquals(measureUnitVersion, measureUnitDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new ingredientDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewIngredientDTOVersionAndReturnIt() {
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final Long sourceVersion = source.getVersion();
        final Long ingredientDTOVersion = ingredientDTO.getVersion();
        assertEquals(sourceVersion, ingredientDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new ingredientDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientDTOAndReturnIt() {
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
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceMeasureUnitId = source.getId();
            final String sourceMeasureUnitName = source.getName();
            final Long sourceMeasureUnitVersion = source.getVersion();
            target.setId(sourceMeasureUnitId);
            target.setName(sourceMeasureUnitName);
            target.setVersion(sourceMeasureUnitVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
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

        final Long measureUnitId = source.getMeasureUnit().getId();
        final Long measureUnitDTOId = ingredientDTO.getMeasureUnitDTO().getId();
        assertEquals(measureUnitId, measureUnitDTOId);

        final String measureUnitName = source.getMeasureUnit().getName();
        final String measureUnitDTOName = ingredientDTO.getMeasureUnitDTO().getName();
        assertEquals(measureUnitName, measureUnitDTOName);

        final Long measureUnitVersion = source.getMeasureUnit().getVersion();
        final Long measureUnitDTOVersion = ingredientDTO.getMeasureUnitDTO().getVersion();
        assertEquals(measureUnitVersion, measureUnitDTOVersion);

        final Long sourceVersion = source.getVersion();
        final Long ingredientDTOVersion = ingredientDTO.getVersion();
        assertEquals(sourceVersion, ingredientDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(HasLongId.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}