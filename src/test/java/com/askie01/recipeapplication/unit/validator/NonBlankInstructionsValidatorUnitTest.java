package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.model.value.HasInstructions;
import com.askie01.recipeapplication.validator.InstructionsValidator;
import com.askie01.recipeapplication.validator.NonBlankInstructionsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NonBlankInstructionsValidator unit test")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class NonBlankInstructionsValidatorUnitTest {

    private InstructionsValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new NonBlankInstructionsValidator();
    }

    @Test
    @DisplayName("isValid method should return true when source instructions is non-blank")
    void isValid_whenSourceInstructionsIsNonBlank_returnsTrue() {
        final HasInstructions argument = HasInstructionsTestBuilder.builder()
                .instructions("instructions")
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @ParameterizedTest(name = "isValid method should return false when source instructions is blank")
    @ValueSource(strings = {"", "   "})
    void isValid_whenSourceInstructionsIsBlank_returnsFalse(String instructions) {
        final HasInstructions argument = HasInstructionsTestBuilder.builder()
                .instructions(instructions)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source instructions is null")
    void isValid_whenSourceHasNullInstructions_throwsNullPointerException() {
        final HasInstructions argument = HasInstructionsTestBuilder.builder()
                .instructions(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source is null")
    void isValid_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}