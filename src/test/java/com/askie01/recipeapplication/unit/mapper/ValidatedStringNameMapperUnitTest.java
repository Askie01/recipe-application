package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.comparator.StringNameTestComparator;
import com.askie01.recipeapplication.comparator.StringNameValueTestComparator;
import com.askie01.recipeapplication.mapper.StringNameMapper;
import com.askie01.recipeapplication.mapper.ValidatedStringNameMapper;
import com.askie01.recipeapplication.model.value.HasStringName;
import com.askie01.recipeapplication.validator.StringNameValidator;
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
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedStringNameMapper unit tests")
class ValidatedStringNameMapperUnitTest {

    @Mock
    private StringNameValidator validator;
    private StringNameMapper mapper;
    private HasStringName source;
    private HasStringName target;

    private StringNameTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedStringNameMapper(validator);
        this.source = HasStringNameTestBuilder.builder()
                .name("Source name")
                .build();
        this.target = HasStringNameTestBuilder.builder()
                .name("Target name")
                .build();
        this.comparator = new StringNameValueTestComparator();
    }

    @Test
    @DisplayName("map method should map source name to target name when source name is valid")
    void map_whenSourceIsValid_mapsSourceNameToTargetName() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final boolean equalNames = comparator.compare(source, target);
        assertTrue(equalNames);
    }

    @Test
    @DisplayName("map method should not map source name to target name when source name is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceNameToTargetName() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final boolean equalNames = comparator.compare(source, target);
        assertFalse(equalNames);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source name is null")
    void map_whenSourceNameIsNull_throwsNullPointerException() {
        source.setName(null);
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