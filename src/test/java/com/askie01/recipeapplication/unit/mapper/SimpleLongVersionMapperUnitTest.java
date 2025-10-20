package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.comparator.LongVersionValueTestComparator;
import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.mapper.SimpleLongVersionMapper;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("SimpleLongVersionMapper unit tests")
class SimpleLongVersionMapperUnitTest {

    private HasLongVersion source;
    private HasLongVersion target;

    private LongVersionMapper mapper;
    private LongVersionTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleLongVersionMapper();
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
        this.target = HasLongVersionTestBuilder.builder()
                .version(2L)
                .build();
        this.comparator = new LongVersionValueTestComparator();
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