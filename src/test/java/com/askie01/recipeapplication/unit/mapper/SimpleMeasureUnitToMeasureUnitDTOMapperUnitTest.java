package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
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
@DisplayName("SimpleMeasureUnitToMeasureUnitDTOMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleMeasureUnitToMeasureUnitDTOMapperUnitTest {

    private MeasureUnit source;
    private MeasureUnitDTO target;
    private MeasureUnitToMeasureUnitDTOMapper mapper;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @BeforeEach
    void setUp() {
        this.source = getTestMeasureUnit();
        this.target = getTestMeasureUnitDTO();
        this.mapper = new SimpleMeasureUnitToMeasureUnitDTOMapper(idMapper, nameMapper, versionMapper);
    }

    private static MeasureUnit getTestMeasureUnit() {
        return MeasureUnit.builder()
                .id(2L)
                .name("Test measure unit")
                .version(2L)
                .build();
    }

    private static MeasureUnitDTO getTestMeasureUnitDTO() {
        return MeasureUnitDTO.builder()
                .id(1L)
                .name("Test measure unitDTO")
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
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
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

        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(HasLongId.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(HasLongId.class), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToDTO method should map source id to new MeasureUnitDTO id and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewMeasureUnitDTOIdAndReturnIt() {
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
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        assertEquals(sourceId, measureUnitDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new MeasureUnitDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewMeasureUnitDTONameAndReturnIt() {
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
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final String sourceName = source.getName();
        final String measureUnitDTOName = measureUnitDTO.getName();
        assertEquals(sourceName, measureUnitDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new MeasureUnitDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewMeasureUnitDTOVersionAndReturnIt() {
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
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final Long sourceVersion = source.getVersion();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        assertEquals(sourceVersion, measureUnitDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map source id, name and version to new MeasureUnitDTO id, name and version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdNameVersionToNewMeasureUnitDTOIdNameVersionAndReturnIt() {
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
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long measureUnitDTOId = measureUnitDTO.getId();
        assertEquals(sourceId, measureUnitDTOId);

        final String sourceName = source.getName();
        final String measureUnitDTOName = measureUnitDTO.getName();
        assertEquals(sourceName, measureUnitDTOName);

        final Long sourceVersion = source.getVersion();
        final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
        assertEquals(sourceVersion, measureUnitDTOVersion);
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