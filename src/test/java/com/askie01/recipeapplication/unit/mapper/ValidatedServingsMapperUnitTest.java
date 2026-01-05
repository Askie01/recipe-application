package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasServingsTestBuilder;
import com.askie01.recipeapplication.comparator.ServingsTestComparator;
import com.askie01.recipeapplication.comparator.ServingsValueTestComparator;
import com.askie01.recipeapplication.mapper.ServingsMapper;
import com.askie01.recipeapplication.mapper.ValidatedServingsMapper;
import com.askie01.recipeapplication.model.value.HasServings;
import com.askie01.recipeapplication.validator.ServingsValidator;
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
@DisplayName("ValidatedServingsMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ValidatedServingsMapperUnitTest {

    private HasServings source;
    private HasServings target;
    private ServingsMapper mapper;

    @Mock
    private ServingsValidator validator;
    private ServingsTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedServingsMapper(validator);
        this.source = HasServingsTestBuilder.builder()
                .servings(1.0)
                .build();
        this.target = HasServingsTestBuilder.builder()
                .servings(5.0)
                .build();
        this.comparator = new ServingsValueTestComparator();
    }

    @Test
    @DisplayName("map method should map source servings to target servings when source is valid")
    void map_whenSourceIsValid_mapsSourceServingsToTargetServings() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final boolean equalServings = comparator.compare(source, target);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("map method should not map source servings to target servings when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceServingsToTargetServings() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final boolean equalServings = comparator.compare(source, target);
        assertFalse(equalServings);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source servings is null")
    void map_whenSourceServingsIsNull_throwsNullPointerException() {
        source.setServings(null);
        when(validator.isValid(source)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        when(validator.isValid(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        when(validator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}