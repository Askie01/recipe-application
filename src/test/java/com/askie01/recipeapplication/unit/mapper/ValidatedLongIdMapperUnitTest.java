package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongIdValueTestComparator;
import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.mapper.ValidatedLongIdMapper;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ValidatedLongIdMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ValidatedLongIdMapperUnitTest {

    private HasLongId source;
    private HasLongId target;
    private LongIdMapper mapper;

    @Mock
    private LongIdValidator validator;
    private LongIdTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedLongIdMapper(validator);
        this.source = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
        this.target = HasLongIdTestBuilder.builder()
                .id(2L)
                .build();
        this.comparator = new LongIdValueTestComparator();
    }

    @Test
    @DisplayName("map method should map source id to target id when source id is valid")
    void map_whenSourceIdIsValid_mapsSourceIdToTargetId() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final boolean equalId = comparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should not map source id to target id when source id is invalid")
    void map_whenSourceIdIsInvalid_doesNotMapSourceIdToTargetId() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final boolean equalId = comparator.compare(source, target);
        assertFalse(equalId);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        when(validator.isValid(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        when(validator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}