package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.HasDescriptionTestBuilder;
import com.askie01.recipeapplication.model.value.HasDescription;
import com.askie01.recipeapplication.validator.DescriptionValidator;
import com.askie01.recipeapplication.validator.NonBlankDescriptionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("NonBlankDescriptionValidator unit tests")
class NonBlankDescriptionValidatorUnitTest {

    private DescriptionValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new NonBlankDescriptionValidator();
    }

    @Test
    @DisplayName("isValid method should return true when description is non-blank")
    void isValid_whenDescriptionIsNonBlank_returnsTrue() {
        final HasDescription argument = HasDescriptionTestBuilder.builder()
                .description("description")
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @ParameterizedTest(name = "isValid method should return false when description is blank")
    @ValueSource(strings = {"", "   "})
    void isValid_whenDescriptionIsBlank_returnsFalse(String description) {
        final HasDescription argument = HasDescriptionTestBuilder.builder()
                .description(description)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if description in HasDescription is null")
    void isValid_whenDescriptionIsNull_throwsNullPointerException() {
        final HasDescription argument = HasDescriptionTestBuilder.builder()
                .description(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if HasDescription is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}