package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.CategoryCategoryDTOTestComparator;
import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.comparator.StringNameTestComparator;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOTestFactory;
import com.askie01.recipeapplication.factory.CategoryTestFactory;
import com.askie01.recipeapplication.mapper.CategoryDTOToCategoryMapper;
import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleCategoryDTOToCategoryMapperDefaultTestConfiguration.class,
        RandomCategoryTestFactoryDefaultTestConfiguration.class,
        RandomCategoryDTOTestFactoryDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration.class
})
@TestPropertySource(locations = "classpath:simple-categoryDTO-to-category-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleCategoryDTOToCategoryMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleCategoryDTOToCategoryMapperIntegrationTest {

    private CategoryDTO source;
    private Category target;
    private final LongIdTestComparator idComparator;
    private final StringNameTestComparator nameComparator;
    private final LongVersionTestComparator versionComparator;
    private final CategoryCategoryDTOTestComparator categoryComparator;
    private final CategoryTestFactory categoryFactory;
    private final CategoryDTOTestFactory categoryDTOFactory;
    private final CategoryDTOToCategoryMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = categoryDTOFactory.createCategoryDTO();
        this.target = categoryFactory.createCategory();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }


    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map all common field values from source to target if source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToTarget() {
        mapper.map(source, target);
        final boolean equalCategories = categoryComparator.compare(target, source);
        assertTrue(equalCategories);
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
    @DisplayName("mapToEntity method should map source id to new Category id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewCategoryIdAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, category);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new Category name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewCategoryNameAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, category);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new Category version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewCategoryVersionAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, category);
        assertTrue(equalVersion);
    }


    @Test
    @DisplayName("mapToEntity method should map all common field values from source to new Category object and return it if source is present")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToNewCategoryAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final boolean equalCategories = categoryComparator.compare(category, source);
        assertTrue(equalCategories);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}