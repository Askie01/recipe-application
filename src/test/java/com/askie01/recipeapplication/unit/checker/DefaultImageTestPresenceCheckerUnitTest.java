package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.checker.DefaultImageTestPresenceChecker;
import com.askie01.recipeapplication.checker.ImageTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultImageTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultImageTestPresenceCheckerUnitTest {

    private HasImage source;
    private ImageTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultImageTestPresenceChecker();
        this.source = HasImageTestBuilder.builder()
                .image(new byte[2048])
                .build();
    }

    @Test
    @DisplayName("hasImage method should return true when source image is positive length")
    void hasImage_whenSourceImageIsPositiveLength_returnsTrue() {
        final boolean result = checker.hasImage(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasImage method should return false when source image is zero length")
    void hasImage_whenSourceImageIsZeroLength_returnsFalse() {
        source.setImage(new byte[0]);
        final boolean result = checker.hasImage(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasImage method should return false when source image is null")
    void hasImage_whenSourceImageIsNull_returnsFalse() {
        source.setImage(null);
        final boolean result = checker.hasImage(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasImage method should throw NullPointerException when source is null")
    void hasImage_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasImage(null));
    }
}