package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasServingsTestBuilder;
import com.askie01.recipeapplication.configuration.ServingsValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasServings;
import com.askie01.recipeapplication.validator.ServingsValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ServingsValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.servings-type=positive-servings")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("PositiveServingsValidator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class PositiveServingsValidatorIntegrationTest {

    private final ServingsValidator validator;

    @Test
    @DisplayName("isValid method should return true when argument's servings are positive")
    void isValid_whenArgumentServingsArePositive_returnsTrue() {
        final HasServings argument = HasServingsTestBuilder.builder()
                .servings(5d)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when argument's servings are negative")
    void isValid_whenArgumentServingsAreNegative_returnsFalse() {
        final HasServings argument = HasServingsTestBuilder.builder()
                .servings(-5d)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's servings are null")
    void isValid_whenArgumentServingsAreNull_throwsNullPointerException() {
        final HasServings argument = HasServingsTestBuilder.builder()
                .servings(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}