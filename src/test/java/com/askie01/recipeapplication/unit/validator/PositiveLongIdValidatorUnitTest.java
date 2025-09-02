package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
import com.askie01.recipeapplication.validator.PositiveLongIdValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PositiveLongIdValidator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class PositiveLongIdValidatorUnitTest {

    private LongIdValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new PositiveLongIdValidator();
    }

    @Test
    @DisplayName("isValid method should return true when id in HasLongId is positive")
    void isValid_whenIdIsPositive_returnsTrue() {
        final HasLongId argument = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
        final boolean actualResult = validator.isValid(argument);
        assertTrue(actualResult);
    }

    @Test
    @DisplayName("isValid method should return false when id in HasLongId is negative")
    void isValid_whenIdIsNegative_returnsFalse() {
        final HasLongId argument = HasLongIdTestBuilder.builder()
                .id(-1L)
                .build();
        final boolean actualResult = validator.isValid(argument);
        assertFalse(actualResult);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if id in HasLongId is null")
    void isValid_whenIdIsNull_throwsNullPointerException() {
        final HasLongId argument = HasLongIdTestBuilder.builder()
                .id(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if HasLongId is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}