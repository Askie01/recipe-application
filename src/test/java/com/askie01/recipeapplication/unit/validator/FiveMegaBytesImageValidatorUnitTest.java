package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.model.value.HasImage;
import com.askie01.recipeapplication.validator.FiveMegaBytesImageValidator;
import com.askie01.recipeapplication.validator.ImageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FiveMegaBytesImageValidator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class FiveMegaBytesImageValidatorUnitTest {

    private ImageValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new FiveMegaBytesImageValidator();
    }

    @Test
    @DisplayName("isValid method should return true when argument's image size is above 0 MBs")
    void isValid_whenArgumentImageSizeIsAboveZeroMegaBytes_returnsTrue() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(new byte[2 * 1024 * 1024])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return true when argument's image size is exactly 5 MBs")
    void isValid_whenArgumentImageSizeIsExactlyFiveMegaBytes_returnsTrue() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(new byte[5 * 1024 * 1024])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return true when argument's image size is 0 Bytes")
    void isValid_whenArgumentImageSizeIsZeroBytes_returnsTrue() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(new byte[0])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false if argument's image is null")
    void isValid_whenArgumentImageIsNull_returnsFalse() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(null)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}