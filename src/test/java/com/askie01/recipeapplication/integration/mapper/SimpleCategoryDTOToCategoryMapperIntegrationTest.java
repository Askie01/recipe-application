package com.askie01.recipeapplication.integration.mapper;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleCategoryDTOToCategoryMapperConfiguration.class,
        ValidatedLongIdMapperConfiguration.class,
        PositiveLongIdValidatorConfiguration.class,
        ValidatedStringNameMapperConfiguration.class,
        NonBlankStringNameValidatorConfiguration.class,
        ValidatedLongVersionMapperConfiguration.class,
        PositiveLongVersionValidatorConfiguration.class,
        RandomCategoryDTOTestFactoryTestConfiguration.class,
        RandomCategoryTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.categoryDTO-to-category-type=simple",
        "component.mapper.id-type=validated-long-id",
        "component.validator.id-type=positive-long-id",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.version-type=validated-long-version",
        "component.validator.version-type=positive-long-version"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("SimpleCategoryDTOToCategoryMapper integration tests")
class SimpleCategoryDTOToCategoryMapperIntegrationTest {

    private final CategoryDTOToCategoryMapper mapper;
    private final CategoryDTOTestFactory categoryDTOFactory;
    private final CategoryTestFactory categoryFactory;
    private CategoryDTO source;
    private Category target;

    @BeforeEach
    void setUp() {
        this.source = categoryDTOFactory.createCategoryDTO();
        this.target = categoryFactory.createCategory();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }


    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
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
        final Long sourceId = source.getId();
        final Long categoryId = category.getId();
        assertEquals(sourceId, categoryId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new Category name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewCategoryNameAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String categoryName = category.getName();
        assertEquals(sourceName, categoryName);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new Category version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewCategoryVersionAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long categoryVersion = category.getVersion();
        assertEquals(sourceVersion, categoryVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source id, name and version to new Category id, name and version object and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdNameVersionToCategoryIdNameVersionAndReturnIt() {
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
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}