package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.model.value.HasStringName;
import com.askie01.recipeapplication.validator.NonBlankStringNameValidator;
import com.askie01.recipeapplication.validator.StringNameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NonBlankStringNameValidator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class NonBlankStringNameValidatorUnitTest {

    private StringNameValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new NonBlankStringNameValidator();
    }

    @Test
    @DisplayName("isValid method should return true when name is non-blank")
    void isValid_whenNameIsNonBlank_returnsTrue() {
        final HasStringName argument = HasStringNameTestBuilder.builder()
                .name("name")
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @ParameterizedTest(name = "isValid method should return false when name is blank")
    @ValueSource(strings = {"", "   "})
    void isValid_whenNameIsBlank_returnsFalse(String name) {
        final HasStringName argument = HasStringNameTestBuilder.builder()
                .name(name)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if name in HasStringName is null")
    void isValid_whenNameIsNull_throwsNullPointerException() {
        final HasStringName argument = HasStringNameTestBuilder.builder()
                .name(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if HasStringName is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}