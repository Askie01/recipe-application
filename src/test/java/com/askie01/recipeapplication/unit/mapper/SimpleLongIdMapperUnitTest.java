package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongIdValueTestComparator;
import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.mapper.SimpleLongIdMapper;
import com.askie01.recipeapplication.model.value.HasLongId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("SimpleLongIdMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleLongIdMapperUnitTest {

    private HasLongId source;
    private HasLongId target;
    private LongIdMapper mapper;
    private LongIdTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleLongIdMapper();
        this.source = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
        this.target = HasLongIdTestBuilder.builder()
                .id(2L)
                .build();
        this.comparator = new LongIdValueTestComparator();
    }

    @Test
    @DisplayName("map method should map source id to target id when source and target are present")
    void map_shouldMapSourceIdToTargetId_whenSourceAndTargetArePresent() {
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