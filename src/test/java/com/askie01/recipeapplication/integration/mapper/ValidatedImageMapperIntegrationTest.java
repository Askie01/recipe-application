package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.configuration.ImageMapperConfiguration;
import com.askie01.recipeapplication.configuration.ImageValidatorConfiguration;
import com.askie01.recipeapplication.mapper.ImageMapper;
import com.askie01.recipeapplication.model.value.HasImage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ImageMapperConfiguration.class)
@Import(value = ImageValidatorConfiguration.class)
@TestPropertySource(properties = {
        "component.mapper.image-type=validated-image",
        "component.validator.image-type=five-mega-bytes-image"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedImageMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedImageMapperIntegrationTest {

    private HasImage source;
    private HasImage target;
    private final ImageMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = HasImageTestBuilder.builder()
                .image(new byte[1024])
                .build();
        this.target = HasImageTestBuilder.builder()
                .image(new byte[2 * 1024])
                .build();
    }

    @Test
    @DisplayName("map method should map source image to target image when source image is valid")
    void map_whenSourceIsValid_mapsSourceImageToTargetImage() {
        mapper.map(source, target);
        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertArrayEquals(sourceImage, targetImage);
    }

    @Test
    @DisplayName("map method should not map source image to target image when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceImageToTargetImage() {
        source.setImage(new byte[6 * 1024 * 1024]);
        mapper.map(source, target);
        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertNotEquals(sourceImage, targetImage);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}