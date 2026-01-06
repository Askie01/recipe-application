package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.comparator.ImageTestComparator;
import com.askie01.recipeapplication.configuration.ImageValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.model.value.HasImage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ImageValueTestComparatorTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ImageValueTestComparator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ImageValueTestComparatorIntegrationTest {

    private final ImageTestComparator comparator;
    private HasImage source;
    private HasImage target;

    @BeforeEach
    void setUp() {
        this.source = HasImageTestBuilder.builder()
                .image(new byte[2048])
                .build();
        this.target = HasImageTestBuilder.builder()
                .image(new byte[1024])
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source image is equal to target image")
    void compare_whenSourceImageIsEqualToTargetImage_returnsTrue() {
        final byte[] sourceImage = source.getImage();
        target.setImage(sourceImage);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source image is not equal to target image")
    void compare_whenSourceImageIsNotEqualToTargetImage_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source image is null")
    void compare_whenSourceImageIsNull_returnsFalse() {
        source.setImage(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target image is null")
    void compare_whenTargetImageIsNull_returnsFalse() {
        target.setImage(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when source is null")
    void compare_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, target));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when target is null")
    void compare_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(source, null));
    }
}