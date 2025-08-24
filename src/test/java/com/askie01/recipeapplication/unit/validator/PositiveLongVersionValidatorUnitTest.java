package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.TestHasLongVersionBuilder;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.validator.LongVersionValidator;
import com.askie01.recipeapplication.validator.PositiveLongVersionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("PositiveLongVersionValidator unit tests")
class PositiveLongVersionValidatorUnitTest {

    private LongVersionValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new PositiveLongVersionValidator();
    }

    @Test
    @DisplayName("isValid method should return true when source version is positive")
    void isValid_whenSourceVersionIsPositive_returnsTrue() {
        final HasLongVersion argument = TestHasLongVersionBuilder.builder()
                .version(5L)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when source version is negative")
    void isValid_whenSourceVersionIsNegative_returnsFalse() {
        final HasLongVersion argument = TestHasLongVersionBuilder.builder()
                .version(-5L)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source version is null")
    void isValid_whenSourceVersionIsNull_throwsNullPointerException() {
        final HasLongVersion argument = TestHasLongVersionBuilder.builder()
                .version(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source is null")
    void isValid_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}