package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.mapper.InstructionsMapper;
import com.askie01.recipeapplication.mapper.ValidatedInstructionsMapper;
import com.askie01.recipeapplication.model.value.HasInstructions;
import com.askie01.recipeapplication.validator.InstructionsValidator;
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
@DisplayName("ValidatedInstructionsMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ValidatedInstructionsMapperUnitTest {

    private HasInstructions source;
    private HasInstructions target;
    private InstructionsMapper mapper;

    @Mock
    private InstructionsValidator validator;

    @BeforeEach
    void setUp() {
        this.source = HasInstructionsTestBuilder.builder()
                .instructions("Source instructions")
                .build();
        this.target = HasInstructionsTestBuilder.builder()
                .instructions("Target instructions")
                .build();
        this.mapper = new ValidatedInstructionsMapper(validator);
    }

    @Test
    @DisplayName("map method should map source instructions to target instructions when source is valid")
    void map_whenSourceInstructionsIsValid_mapsSourceInstructionsToTargetInstructions() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);
    }

    @Test
    @DisplayName("map method should not map source instructions to target instructions when source is invalid")
    void map_whenSourceInstructionsIsInvalid_doesNotMapSourceInstructionsToTargetInstructions() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertNotEquals(sourceInstructions, targetInstructions);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source instructions are null")
    void map_whenSourceInstructionsIsNull_throwsNullPointerException() {
        source.setInstructions(null);
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