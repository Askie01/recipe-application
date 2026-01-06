package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.configuration.NonBlankStringNameValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasStringName;
import com.askie01.recipeapplication.validator.StringNameValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = NonBlankStringNameValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.name-type=non-blank-string")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("NonBlankStringNameValidator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class NonBlankStringNameValidatorIntegrationTest {

    private final StringNameValidator validator;

    @Test
    @DisplayName("isValid method should return true when argument's name is non-blank")
    void isValid_whenArgumentNameIsNonBlank_returnsTrue() {
        final HasStringName argument = HasStringNameTestBuilder.builder()
                .name("name")
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @ParameterizedTest(name = "isValid method should return false when argument's name is blank")
    @ValueSource(strings = {"", "   "})
    void isValid_whenArgumentNameIsBlank_returnsFalse(String name) {
        final HasStringName argument = HasStringNameTestBuilder.builder()
                .name(name)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's name is null")
    void isValid_whenArgumentNameIsNull_throwsNullPointerException() {
        final HasStringName argument = HasStringNameTestBuilder.builder()
                .name(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}