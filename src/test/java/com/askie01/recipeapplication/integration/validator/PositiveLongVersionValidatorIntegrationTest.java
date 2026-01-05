package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.configuration.PositiveLongVersionValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.validator.LongVersionValidator;
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
@ContextConfiguration(classes = PositiveLongVersionValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.version-type=positive-long-version")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("PositiveLongVersionValidator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class PositiveLongVersionValidatorIntegrationTest {

    private final LongVersionValidator validator;

    @Test
    @DisplayName("isValid method should return true when argument's version is positive")
    void isValid_whenArgumentVersionIsPositive_returnsTrue() {
        final HasLongVersion argument = HasLongVersionTestBuilder.builder()
                .version(5L)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when argument's version is negative")
    void isValid_whenArgumentVersionIsNegative_returnsFalse() {
        final HasLongVersion argument = HasLongVersionTestBuilder.builder()
                .version(-5L)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's version is null")
    void isValid_whenArgumentVersionIsNull_throwsNullPointerException() {
        final HasLongVersion argument = HasLongVersionTestBuilder.builder()
                .version(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}