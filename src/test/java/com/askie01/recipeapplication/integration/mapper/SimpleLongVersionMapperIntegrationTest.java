package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.configuration.LongVersionValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.SimpleLongVersionMapperConfiguration;
import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.model.value.HasLongVersion;
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
        SimpleLongVersionMapperConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.version-type=simple-long-version"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("SimpleLongVersionMapper integration tests")
class SimpleLongVersionMapperIntegrationTest {

    private HasLongVersion source;
    private HasLongVersion target;

    private final LongVersionMapper mapper;
    private final LongVersionTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
        this.target = HasLongVersionTestBuilder.builder()
                .version(2L)
                .build();
    }

    @Test
    @DisplayName("map method should map source version to target version when source and target are present")
    void map_shouldMapSourceVersionToTargetVersion_whenSourceAndTargetArePresent() {
        mapper.map(source, target);
        final boolean equalId = comparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}