package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.CategoryCategoryDTOTestComparator;
import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.comparator.StringNameTestComparator;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.factory.CategoryDTOTestFactory;
import com.askie01.recipeapplication.factory.CategoryTestFactory;
import com.askie01.recipeapplication.mapper.CategoryToCategoryDTOMapper;
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
        SimpleCategoryToCategoryDTOMapperDefaultTestConfiguration.class,
        RandomCategoryTestFactoryDefaultTestConfiguration.class,
        RandomCategoryDTOTestFactoryDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration.class
})
@TestPropertySource(locations = "classpath:simple-category-to-categoryDTO-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleCategoryToCategoryDTOMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleCategoryToCategoryDTOMapperIntegrationTest {

    private Category source;
    private CategoryDTO target;
    private final CategoryTestFactory categoryFactory;
    private final CategoryDTOTestFactory categoryDTOFactory;
    private final CategoryToCategoryDTOMapper mapper;
    private final LongIdTestComparator idComparator;
    private final StringNameTestComparator nameComparator;
    private final LongVersionTestComparator versionComparator;
    private final CategoryCategoryDTOTestComparator categoryComparator;

    @BeforeEach
    void setUp() {
        this.source = categoryFactory.createCategory();
        this.target = categoryDTOFactory.createCategoryDTO();
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
        final boolean equalCategories = categoryComparator.compare(source, target);
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
    @DisplayName("mapToDTO method should map source id to new CategoryDTO id and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewCategoryDTOIdAndReturnIt() {
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final boolean equalId = idComparator.compare(source, categoryDTO);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new CategoryDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewCategoryDTONameAndReturnIt() {
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final boolean equalName = nameComparator.compare(source, categoryDTO);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new CategoryDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewCategoryDTOVersionAndReturnIt() {
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final boolean equalVersion = versionComparator.compare(source, categoryDTO);
        assertTrue(equalVersion);
    }


    @Test
    @DisplayName("mapToDTO method should map all common field values from source to new CategoryDTO object and return it if source is present")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToNewCategoryDTOAndReturnIt() {
        final CategoryDTO categoryDTO = mapper.mapToDTO(source);
        final boolean equalCategories = categoryComparator.compare(source, categoryDTO);
        assertTrue(equalCategories);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}