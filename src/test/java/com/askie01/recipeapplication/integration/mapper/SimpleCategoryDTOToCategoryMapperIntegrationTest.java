package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.mapper.CategoryDTOToCategoryMapper;
import com.askie01.recipeapplication.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = CategoryDTOToCategoryMapperConfiguration.class)
@Import(value = {
        LongIdMapperConfiguration.class,
        StringNameMapperConfiguration.class,
        StringNameValidatorConfiguration.class,
        LongVersionMapperConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.categoryDTO-to-category-type=simple",
        "component.mapper.id-type=simple-long-id",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.version-type=simple-long-version"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleCategoryDTOToCategoryMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleCategoryDTOToCategoryMapperIntegrationTest {

    private CategoryDTO source;
    private Category target;
    private final CategoryDTOToCategoryMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestCategoryDTO();
        this.target = getTestCategory();
    }

    private static CategoryDTO getTestCategoryDTO() {
        return CategoryDTO.builder()
                .id(1L)
                .name("Test categoryDTO")
                .version(1L)
                .build();
    }

    private static Category getTestCategory() {
        return Category.builder()
                .id(2L)
                .name("Test category")
                .version(2L)
                .build();
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
    @DisplayName("map method should map all common field values from source to target if source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToTarget() {
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
    @DisplayName("mapToEntity method should map all common field values from source to new Category object and return it if source is present")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldValuesFromSourceToNewCategoryAndReturnIt() {
        final Category category = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long categoryId = category.getId();
        assertEquals(sourceId, categoryId);

        final String sourceName = source.getName();
        final String categoryName = category.getName();
        assertEquals(sourceName, categoryName);

        final Long sourceVersion = source.getVersion();
        final Long categoryVersion = category.getVersion();
        assertEquals(sourceVersion, categoryVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}