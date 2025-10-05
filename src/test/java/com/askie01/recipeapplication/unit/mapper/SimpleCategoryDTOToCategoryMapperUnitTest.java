package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.comparator.*;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("SimpleCategoryDTOToCategoryMapper unit tests")
class SimpleCategoryDTOToCategoryMapperUnitTest {

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private LongVersionMapper versionMapper;
    private CategoryDTOToCategoryMapper mapper;
    private CategoryDTO source;
    private Category target;

    private CategoryCategoryDTOTestComparator categoryComparator;
    private LongIdTestComparator idComparator;
    private StringNameTestComparator nameComparator;
    private LongVersionTestComparator versionComparator;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleCategoryDTOToCategoryMapper(idMapper, nameMapper, versionMapper);
        final Faker faker = new Faker();
        this.source = new RandomCategoryDTOTestFactory(faker).createCategoryDTO();
        this.target = new RandomCategoryTestFactory(faker).createCategory();

        this.idComparator = new LongIdValueTestComparator();
        this.nameComparator = new StringNameValueTestComparator();
        this.versionComparator = new LongVersionValueTestComparator();
        this.categoryComparator = new CategoryCategoryDTOValueTestComparator(
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
    @DisplayName("map method should map all common field values from source to target if source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToTarget() {
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
        final boolean equalCategories = categoryComparator.compare(target, source);
        assertTrue(equalCategories);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Category.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
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
        }).when(idMapper).map(any(CategoryDTO.class), any(Category.class));
        final Category category = mapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, category);
        assertTrue(equalId);
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
        }).when(nameMapper).map(any(CategoryDTO.class), any(Category.class));
        final Category category = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, category);
        assertTrue(equalName);
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
        }).when(versionMapper).map(any(CategoryDTO.class), any(Category.class));
        final Category category = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, category);
        assertTrue(equalVersion);
    }


    @Test
    @DisplayName("mapToEntity method should map all common field values from source to new Category object and return it if source is present")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToNewCategoryAndReturnIt() {
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final Long categoryDTOId = categoryDTO.getId();
            category.setId(categoryDTOId);
            return null;
        }).when(idMapper).map(any(CategoryDTO.class), any(Category.class));
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final String categoryDTOName = categoryDTO.getName();
            category.setName(categoryDTOName);
            return null;
        }).when(nameMapper).map(any(CategoryDTO.class), any(Category.class));
        doAnswer(invocation -> {
            final CategoryDTO categoryDTO = invocation.getArgument(0);
            final Category category = invocation.getArgument(1);
            final Long categoryDTOVersion = categoryDTO.getVersion();
            category.setVersion(categoryDTOVersion);
            return null;
        }).when(versionMapper).map(any(CategoryDTO.class), any(Category.class));

        final Category category = mapper.mapToEntity(source);
        final boolean equalCategories = categoryComparator.compare(category, source);
        assertTrue(equalCategories);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Category.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}