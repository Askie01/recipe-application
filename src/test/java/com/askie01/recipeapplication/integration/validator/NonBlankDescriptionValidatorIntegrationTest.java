package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasDescriptionTestBuilder;
import com.askie01.recipeapplication.configuration.DescriptionValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasDescription;
import com.askie01.recipeapplication.validator.DescriptionValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = DescriptionValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.description-type=non-blank-description")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("NonBlankDescriptionValidator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class NonBlankDescriptionValidatorIntegrationTest {

    private final DescriptionValidator validator;

    @Test
    @DisplayName("isValid method should return true when argument's description is non-blank")
    void isValid_whenArgumentDescriptionIsNonBlank_returnsTrue() {
        final HasDescription argument = HasDescriptionTestBuilder.builder()
                .description("description")
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @ParameterizedTest(name = "isValid method should return false when argument's description is blank")
    @ValueSource(strings = {"", "   "})
    void isValid_whenArgumentDescriptionIsBlank_returnsFalse(String description) {
        final HasDescription argument = HasDescriptionTestBuilder.builder()
                .description(description)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's description is null")
    void isValid_whenArgumentDescriptionIsNull_throwsNullPointerException() {
        final HasDescription argument = HasDescriptionTestBuilder.builder()
                .description(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}