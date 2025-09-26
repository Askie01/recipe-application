package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.configuration.NonBlankInstructionsValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasInstructions;
import com.askie01.recipeapplication.validator.InstructionsValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NonBlankInstructionsValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.instructions-type=non-blank-instructions")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("NonBlankInstructionsValidator integration tests")
class NonBlankInstructionsValidatorIntegrationTest {

    private final InstructionsValidator validator;

    @Test
    @DisplayName("isValid method should return true when argument's instructions are non-blank")
    void isValid_whenArgumentInstructionsAreNonBlank_returnsTrue() {
        final HasInstructions argument = HasInstructionsTestBuilder.builder()
                .instructions("instructions")
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @ParameterizedTest(name = "isValid method should return false when argument's instructions are blank")
    @ValueSource(strings = {"", "   "})
    void isValid_whenArgumentInstructionsAreBlank_returnsFalse(String instructions) {
        final HasInstructions argument = HasInstructionsTestBuilder.builder()
                .instructions(instructions)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's instructions are null")
    void isValid_whenArgumentInstructionsAreNull_throwsNullPointerException() {
        final HasInstructions argument = HasInstructionsTestBuilder.builder()
                .instructions(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}