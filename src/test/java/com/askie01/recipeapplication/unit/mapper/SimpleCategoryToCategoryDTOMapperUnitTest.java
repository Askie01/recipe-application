package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Category;
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
@DisplayName("SimpleCategoryToCategoryDTOMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleCategoryToCategoryDTOMapperUnitTest {

    private Category source;
    private CategoryDTO target;
    private CategoryToCategoryDTOMapper mapper;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @BeforeEach
    void setUp() {
        this.source = getTestCategory();
        this.target = getTestCategoryDTO();
        this.mapper = new SimpleCategoryToCategoryDTOMapper(idMapper, nameMapper, versionMapper);
    }

    private static Category getTestCategory() {
        return Category.builder()
                .id(2L)
                .name("Test category")
                .version(2L)
                .build();
    }

    private static CategoryDTO getTestCategoryDTO() {
        return CategoryDTO.builder()
                .id(1L)
                .name("Test categoryDTO")
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
    @DisplayName("map method should map all common field values from source to target if source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToTarget() {
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
                .map(isNull(), any());
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToDTO method should map source id to new CategoryDTO id and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewCategoryDTOIdAndReturnIt() {
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
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long categoryDTOId = categoryDTO.getId();
        assertEquals(sourceId, categoryDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new CategoryDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewCategoryDTONameAndReturnIt() {
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
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final String sourceName = source.getName();
        final String categoryDTOName = categoryDTO.getName();
        assertEquals(sourceName, categoryDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new CategoryDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewCategoryDTOVersionAndReturnIt() {
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
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final Long sourceVersion = source.getVersion();
        final Long categoryDTOVersion = categoryDTO.getVersion();
        assertEquals(sourceVersion, categoryDTOVersion);
    }


    @Test
    @DisplayName("mapToDTO method should map all common field values from source to new CategoryDTO object and return it if source is present")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToNewCategoryDTOAndReturnIt() {
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
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long categoryDTOId = categoryDTO.getId();
        assertEquals(sourceId, categoryDTOId);

        final String sourceName = source.getName();
        final String categoryDTOName = categoryDTO.getName();
        assertEquals(sourceName, categoryDTOName);

        final Long sourceVersion = source.getVersion();
        final Long categoryDTOVersion = categoryDTO.getVersion();
        assertEquals(sourceVersion, categoryDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any());
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}