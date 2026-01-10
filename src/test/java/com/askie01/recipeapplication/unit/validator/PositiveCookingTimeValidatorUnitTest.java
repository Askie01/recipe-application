package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.HasCookingTimeTestBuilder;
import com.askie01.recipeapplication.model.value.HasCookingTime;
import com.askie01.recipeapplication.validator.CookingTimeValidator;
import com.askie01.recipeapplication.validator.PositiveCookingTimeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PositiveCookingTimeValidator unit test")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class PositiveCookingTimeValidatorUnitTest {

    private CookingTimeValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new PositiveCookingTimeValidator();
    }

    @Test
    @DisplayName("isValid method should return true when argument's cooking time is positive")
    void isValid_whenArgumentCookingTimeIsPositive_returnsTrue() {
        final HasCookingTime argument = HasCookingTimeTestBuilder.builder()
                .cookingTime(5)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when argument's cooking time is negative")
    void isValid_whenArgumentCookingTimeIsNegative_returnsFalse() {
        final HasCookingTime argument = HasCookingTimeTestBuilder.builder()
                .cookingTime(-5)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's cooking time is null")
    void isValid_whenArgumentCookingTimeIsNull_throwsNullPointerException() {
        final HasCookingTime argument = HasCookingTimeTestBuilder.builder()
                .cookingTime(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}