package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasCookingTimeTestBuilder;
import com.askie01.recipeapplication.configuration.PositiveCookingTimeValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasCookingTime;
import com.askie01.recipeapplication.validator.CookingTimeValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PositiveCookingTimeValidatorConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = "component.validator.cooking-time-type=positive-cooking-time")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("PositiveCookingTimeValidator integration tests")
class PositiveCookingTimeValidatorIntegrationTest {

    private final CookingTimeValidator validator;

    @Test
    @DisplayName("isValid method should return true when source cooking time is positive")
    void isValid_whenSourceCookingTimeIsPositive_returnsTrue() {
        final HasCookingTime argument = HasCookingTimeTestBuilder.builder()
                .cookingTime(5)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when source cooking time is negative")
    void isValid_whenSourceCookingTimeIsNegative_returnsFalse() {
        final HasCookingTime argument = HasCookingTimeTestBuilder.builder()
                .cookingTime(-5)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source cooking time is null")
    void isValid_whenSourceCookingTimeIsNull_throwsNullPointerException() {
        final HasCookingTime argument = HasCookingTimeTestBuilder.builder()
                .cookingTime(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source is null")
    void isValid_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}