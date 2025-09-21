package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.configuration.FiveMegaBytesImageValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasImage;
import com.askie01.recipeapplication.validator.ImageValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FiveMegaBytesImageValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.image-type=five-mega-bytes-image")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("FiveMegaBytesImageValidator integration tests")
class FiveMegaBytesImageValidatorIntegrationTest {

    private final ImageValidator validator;

    @Test
    @DisplayName("isValid method should return true when image size is above 0 MBs")
    void isValid_whenImageSizeIsAboveZeroMegaBytes_returnsTrue() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(new byte[2 * 1024 * 1024])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return true when image size is exactly 5 MBs")
    void isValid_whenImageSizeIsExactlyFiveMegaBytes_returnsTrue() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(new byte[5 * 1024 * 1024])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return true when image size is 0 Bytes")
    void isValid_whenImageSizeIsZeroBytes_returnsTrue() {
        final HasImage argument = HasImageTestBuilder.builder()
                .image(new byte[0])
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if image in HasImage is null")
    void isValid_whenImageIsNull_throwsNullPointerException() {
        final HasImage argument = HasImageTestBuilder.builder()
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