package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.TestHasLongIdBuilder;
import com.askie01.recipeapplication.configuration.PositiveLongIdValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.validator.LongIdValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PositiveLongIdValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = "component.validator.id-type=positive-long-id")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class PositiveLongIdValidatorIntegrationTest {

    private final LongIdValidator validator;

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