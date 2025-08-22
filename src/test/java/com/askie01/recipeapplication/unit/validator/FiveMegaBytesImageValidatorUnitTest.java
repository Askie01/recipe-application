package com.askie01.recipeapplication.unit.validator;

import com.askie01.recipeapplication.builder.TestHasImageBuilder;
import com.askie01.recipeapplication.model.value.HasImage;
import com.askie01.recipeapplication.validator.FiveMegaBytesImageValidator;
import com.askie01.recipeapplication.validator.ImageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FiveMegaBytesImageValidator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class FiveMegaBytesImageValidatorUnitTest {

    private ImageValidator validator;

    @BeforeEach
    void setUp() {
        this.validator = new FiveMegaBytesImageValidator();
    }

    @Test
    @DisplayName("isValid method should return true when image size is above 0 MBs")
    void isValid_whenImageSizeIsAboveZeroMegaBytes_returnsTrue() {
        final HasImage argument = TestHasImageBuilder.builder()
                .image(new byte[2 * 1024 * 1024])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return true when image size is exactly 5 MBs")
    void isValid_whenImageSizeIsExactlyFiveMegaBytes_returnsTrue() {
        final HasImage argument = TestHasImageBuilder.builder()
                .image(new byte[5 * 1024 * 1024])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return true when image size is 0 Bytes")
    void isValid_whenImageSizeIsZeroBytes_returnsTrue() {
        final HasImage argument = TestHasImageBuilder.builder()
                .image(new byte[0])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if image in HasImage is null")
    void isValid_whenImageIsNull_throwsNullPointerException() {
        final HasImage argument = TestHasImageBuilder.builder()
                .image(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if HasImage is null")
    void isValid_whenHasImageIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}