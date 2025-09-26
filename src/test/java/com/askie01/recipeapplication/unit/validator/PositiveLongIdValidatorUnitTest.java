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

@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("PositiveLongIdValidator unit tests")
class PositiveLongIdValidatorUnitTest {

    private LongIdValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new PositiveLongIdValidator();
    }

    @Test
    @DisplayName("isValid method should return true when argument's id is positive")
    void isValid_whenArgumentIdIsPositive_returnsTrue() {
        final HasLongId argument = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
        final boolean actualResult = validator.isValid(argument);
        assertTrue(actualResult);
    }

    @Test
    @DisplayName("isValid method should return false when argument's id is negative")
    void isValid_whenArgumentIdIsNegative_returnsFalse() {
        final HasLongId argument = HasLongIdTestBuilder.builder()
                .id(-1L)
                .build();
        final boolean actualResult = validator.isValid(argument);
        assertFalse(actualResult);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's id is null")
    void isValid_whenArgumentIdIsNull_throwsNullPointerException() {
        final HasLongId argument = HasLongIdTestBuilder.builder()
                .id(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}