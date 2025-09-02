package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.mapper.ValidatedLongVersionMapper;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.validator.LongVersionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedLongVersionMapper unit tests")
class ValidatedLongVersionMapperUnitTest {

    @Mock
    private LongVersionValidator validator;
    private LongVersionMapper mapper;
    private HasLongVersion source;
    private HasLongVersion target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedLongVersionMapper(validator);
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
        this.target = HasLongVersionTestBuilder.builder()
                .version(5L)
                .build();
    }

    @Test
    @DisplayName("map method should map source version to target version when source is valid")
    void map_whenSourceIsValid_mapsSourceVersionToTargetVersion() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should not map source version to target version when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceVersionToTargetVersion() {
        when(validator.isValid(source)).thenReturn(false);
        final Long targetVersionBeforeMapping = target.getVersion();
        mapper.map(source, target);
        final Long targetVersionAfterMapping = target.getVersion();
        assertEquals(targetVersionBeforeMapping, targetVersionAfterMapping);
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