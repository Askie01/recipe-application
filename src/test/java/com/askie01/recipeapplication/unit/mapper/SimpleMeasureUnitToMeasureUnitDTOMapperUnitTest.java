package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.model.value.HasStringName;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private LongIdTestComparator idComparator;
    private StringNameTestComparator nameComparator;
    private LongVersionTestComparator versionComparator;
    private MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;

    @BeforeEach
    void setUp() {
        final Faker faker = new Faker();
        this.mapper = new SimpleMeasureUnitToMeasureUnitDTOMapper(idMapper, nameMapper, versionMapper);
        this.source = new RandomMeasureUnitTestFactory(faker).createMeasureUnit();
        this.target = new RandomMeasureUnitDTOTestFactory(faker).createMeasureUnitDTO();

        this.idComparator = new LongIdValueTestComparator();
        this.nameComparator = new StringNameValueTestComparator();
        this.versionComparator = new LongVersionValueTestComparator();
        this.measureUnitComparator = new MeasureUnitMeasureUnitDTOValueTestComparator(
                idComparator,
                nameComparator,
                versionComparator);
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(source, target);
        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(source, target);
        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(source, target);

        mapper.map(source, target);
        final boolean equalMeasureUnits = measureUnitComparator.compare(source, target);
        assertTrue(equalMeasureUnits);
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
        }).when(idMapper).map(any(HasLongId.class), any(HasLongId.class));
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final boolean equalId = idComparator.compare(measureUnitDTO, source);
        assertTrue(equalId);
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
        }).when(nameMapper).map(any(HasStringName.class), any(HasStringName.class));
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final boolean equalName = nameComparator.compare(measureUnitDTO, source);
        assertTrue(equalName);
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
        }).when(versionMapper).map(any(HasLongVersion.class), any(HasLongVersion.class));
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final boolean equalVersion = versionComparator.compare(measureUnitDTO, source);
        assertTrue(equalVersion);
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
        }).when(idMapper).map(any(HasLongId.class), any(HasLongId.class));
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(any(HasStringName.class), any(HasStringName.class));
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(any(HasLongVersion.class), any(HasLongVersion.class));

        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source);
        final boolean equalMeasureUnits = measureUnitComparator.compare(source, measureUnitDTO);
        assertTrue(equalMeasureUnits);
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