package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.comparator.ImageTestComparator;
import com.askie01.recipeapplication.comparator.ImageValueTestComparator;
import com.askie01.recipeapplication.mapper.ImageMapper;
import com.askie01.recipeapplication.mapper.ValidatedImageMapper;
import com.askie01.recipeapplication.model.value.HasImage;
import com.askie01.recipeapplication.validator.ImageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ValidatedImageMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ValidatedImageMapperUnitTest {

    private HasImage source;
    private HasImage target;
    private ImageMapper mapper;

    @Mock
    private ImageValidator validator;
    private ImageTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedImageMapper(validator);
        this.source = HasImageTestBuilder.builder()
                .image(new byte[1024])
                .build();
        this.target = HasImageTestBuilder.builder()
                .image(new byte[2 * 1024])
                .build();
        this.comparator = new ImageValueTestComparator();
    }

    @Test
    @DisplayName("map method should map source image to target image when source image is valid")
    void map_whenSourceIsValid_mapsSourceImageToTargetImage() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final boolean equalImage = comparator.compare(source, target);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("map method should not map source image to target image when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceImageToTargetImage() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final boolean equalImage = comparator.compare(source, target);
        assertFalse(equalImage);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        when(validator.isValid(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        when(validator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}