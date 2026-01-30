package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasDescriptionTestBuilder;
import com.askie01.recipeapplication.mapper.DescriptionMapper;
import com.askie01.recipeapplication.mapper.ValidatedDescriptionMapper;
import com.askie01.recipeapplication.model.value.HasDescription;
import com.askie01.recipeapplication.validator.DescriptionValidator;
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
@DisplayName("ValidatedDescriptionMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ValidatedDescriptionMapperUnitTest {

    private HasDescription source;
    private HasDescription target;
    private DescriptionMapper mapper;

    @Mock
    private DescriptionValidator validator;

    @BeforeEach
    void setUp() {
        this.source = HasDescriptionTestBuilder.builder()
                .description("source description")
                .build();
        this.target = HasDescriptionTestBuilder.builder()
                .description("target description")
                .build();
        this.mapper = new ValidatedDescriptionMapper(validator);
    }

    @Test
    @DisplayName("map method should map source description to target description when source is valid")
    void map_whenSourceIsValid_mapsSourceDescriptionToTargetDescription() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        assertEquals(sourceDescription, targetDescription);
    }

    @Test
    @DisplayName("map method should not map source description to target description when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceDescriptionToTargetDescription() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        assertNotEquals(sourceDescription, targetDescription);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source description is null")
    void map_whenSourceDescriptionIsNull_throwsNullPointerException() {
        source.setDescription(null);
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