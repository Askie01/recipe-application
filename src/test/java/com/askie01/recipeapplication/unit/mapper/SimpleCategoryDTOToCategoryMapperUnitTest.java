package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.RandomCategoryDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryTestFactory;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Category;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("SimpleCategoryDTOToCategoryMapper unit tests")
class SimpleCategoryDTOToCategoryMapperUnitTest {

    @Mock
    private LongIdMapper longIdMapper;

    @Mock
    private StringNameMapper stringNameMapper;

    @Mock
    private LongVersionMapper longVersionMapper;
    private CategoryDTOToCategoryMapper mapper;
    private CategoryDTO source;
    private Category target;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleCategoryDTOToCategoryMapper(longIdMapper, stringNameMapper, longVersionMapper);
        final Faker faker = new Faker();
        this.source = new RandomCategoryDTOTestFactory(faker).createCategoryDTO();
        this.target = new RandomCategoryTestFactory(faker).createCategory();
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
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
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
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(longVersionMapper).map(source, target);

        mapper.map(source, target);
        final Long sourceId = source.getId();
        final String sourceName = source.getName();
        final Long sourceVersion = source.getVersion();
        final Long targetId = target.getId();
        final String targetName = target.getName();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceId, targetId);
        assertEquals(sourceName, targetName);
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(isNull(), any(Category.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(any(CategoryDTO.class), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new Category id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewCategoryIdAndReturnIt() {
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final Long categoryDTOId = categoryDTO.getId();
            category.setId(categoryDTOId);
            return null;
        }).when(longIdMapper).map(any(CategoryDTO.class), any(Category.class));
        final Category category = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long categoryId = category.getId();
        assertEquals(sourceId, categoryId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new Category name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewCategoryNameAndReturnIt() {
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final String categoryDTOName = categoryDTO.getName();
            category.setName(categoryDTOName);
            return null;
        }).when(stringNameMapper).map(any(CategoryDTO.class), any(Category.class));
        final Category category = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String categoryName = category.getName();
        assertEquals(sourceName, categoryName);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new Category version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewCategoryVersionAndReturnIt() {
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final Long categoryDTOVersion = categoryDTO.getVersion();
            category.setVersion(categoryDTOVersion);
            return null;
        }).when(longVersionMapper).map(any(CategoryDTO.class), any(Category.class));
        final Category category = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long categoryVersion = category.getVersion();
        assertEquals(sourceVersion, categoryVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source id, name and version to new Category id, name and version object and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdNameVersionToCategoryIdNameVersionAndReturnIt() {
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final Long categoryDTOId = categoryDTO.getId();
            category.setId(categoryDTOId);
            return null;
        }).when(longIdMapper).map(any(CategoryDTO.class), any(Category.class));
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final String categoryDTOName = categoryDTO.getName();
            category.setName(categoryDTOName);
            return null;
        }).when(stringNameMapper).map(any(CategoryDTO.class), any(Category.class));
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final Long categoryDTOVersion = categoryDTO.getVersion();
            category.setVersion(categoryDTOVersion);
            return null;
        }).when(longVersionMapper).map(any(CategoryDTO.class), any(Category.class));

        final Category category = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final String sourceName = source.getName();
        final Long sourceVersion = source.getVersion();
        final Long categoryId = category.getId();
        final String categoryName = category.getName();
        final Long categoryVersion = category.getVersion();
        assertEquals(sourceId, categoryId);
        assertEquals(sourceName, categoryName);
        assertEquals(sourceVersion, categoryVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(isNull(), any(Category.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}