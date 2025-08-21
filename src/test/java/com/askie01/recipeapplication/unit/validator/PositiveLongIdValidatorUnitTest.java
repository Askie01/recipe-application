package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.TestHasLongIdBuilder;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
import com.askie01.recipeapplication.validator.PositiveLongIdValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class PositiveLongIdValidatorUnitTest {

    private LongIdValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new PositiveLongIdValidator();
    }

    @Test
    void isValid_whenIdIsPositive_returnsTrue() {
        final HasLongId argument = TestHasLongIdBuilder.builder()
                .id(1L)
                .build();
        final boolean actualResult = validator.isValid(argument);
        assertTrue(actualResult);
    }

    @Test
    void isValid_whenIdIsNegative_returnsFalse() {
        final HasLongId argument = TestHasLongIdBuilder.builder()
                .id(-1L)
                .build();
        final boolean actualResult = validator.isValid(argument);
        assertFalse(actualResult);
    }

    @Test
    void isValid_whenIdIsNull_throwsNullPointerException() {
        final HasLongId argument = TestHasLongIdBuilder.builder()
                .id(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}