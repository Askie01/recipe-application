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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedDescriptionMapper unit tests")
class ValidatedDescriptionMapperUnitTest {

    @Mock
    private DescriptionValidator validator;
    private DescriptionMapper mapper;
    private HasDescription source;
    private HasDescription target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedDescriptionMapper(validator);
        this.source = HasDescriptionTestBuilder.builder()
                .description("source description")
                .build();
        this.target = HasDescriptionTestBuilder.builder()
                .description("target description")
                .build();
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
        final String targetDescriptionBeforeMapping = target.getDescription();
        mapper.map(source, target);
        final String targetDescriptionAfterMapping = target.getDescription();
        assertEquals(targetDescriptionBeforeMapping, targetDescriptionAfterMapping);
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