package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.configuration.PositiveLongIdValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
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
@ContextConfiguration(classes = PositiveLongIdValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.id-type=positive-long-id")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("PositiveLongIdValidator integration tests")
class PositiveLongIdValidatorIntegrationTest {

    private final LongIdValidator validator;

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