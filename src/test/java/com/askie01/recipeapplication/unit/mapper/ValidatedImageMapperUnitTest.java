package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.TestHasImageBuilder;
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedImageMapper unit tests")
class ValidatedImageMapperUnitTest {

    @Mock
    private ImageValidator imageValidator;
    private ImageMapper mapper;
    private HasImage source;
    private HasImage target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedImageMapper(imageValidator);
        this.source = TestHasImageBuilder.builder()
                .image(new byte[1024])
                .build();
        this.target = TestHasImageBuilder.builder()
                .image(new byte[2 * 1024])
                .build();
    }

    @Test
    @DisplayName("map method should map source image to target image when source image is valid")
    void map_whenSourceIsValid_mapsSourceImageToTargetImage() {
        when(imageValidator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertArrayEquals(sourceImage, targetImage);
    }

    @Test
    @DisplayName("map method should not map source image to target image when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceImageToTargetImage() {
        when(imageValidator.isValid(source)).thenReturn(false);
        final byte[] targetImageBeforeMapping = target.getImage();
        mapper.map(source, target);
        final byte[] targetImageAfterMapping = target.getImage();
        assertArrayEquals(targetImageBeforeMapping, targetImageAfterMapping);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        when(imageValidator.isValid(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }


    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        when(imageValidator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}