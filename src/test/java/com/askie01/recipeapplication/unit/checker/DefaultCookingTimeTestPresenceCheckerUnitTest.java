package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasCookingTimeTestBuilder;
import com.askie01.recipeapplication.checker.CookingTimeTestPresenceChecker;
import com.askie01.recipeapplication.checker.DefaultCookingTimeTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasCookingTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultCookingTimeTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultCookingTimeTestPresenceCheckerUnitTest {

    private HasCookingTime source;
    private CookingTimeTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultCookingTimeTestPresenceChecker();
        this.source = HasCookingTimeTestBuilder.builder()
                .cookingTime(1)
                .build();
    }

    @Test
    @DisplayName("hasCookingTime method should return true when source cooking time is positive")
    void hasCookingTime_whenSourceCookingTimeIsPositive_returnsTrue() {
        final boolean result = checker.hasCookingTime(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasCookingTime method should return false when source cooking time is negative")
    void hasCookingTime_whenSourceCookingTimeIsNegative_returnsFalse() {
        source.setCookingTime(-1);
        final boolean result = checker.hasCookingTime(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCookingTime method should return false when source cooking time is zero")
    void hasCookingTime_whenSourceCookingTimeIsZero_returnsFalse() {
        source.setCookingTime(0);
        final boolean result = checker.hasCookingTime(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCookingTime method should return false when source cooking time is null")
    void hasCookingTime_whenSourceCookingTimeIsNull_returnsFalse() {
        source.setCookingTime(null);
        final boolean result = checker.hasCookingTime(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasCookingTime method should throw NullPointerException when source is null")
    void hasCookingTime_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasCookingTime(null));
    }
}