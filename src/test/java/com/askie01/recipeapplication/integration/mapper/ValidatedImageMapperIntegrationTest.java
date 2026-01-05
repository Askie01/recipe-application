package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasImageTestBuilder;
import com.askie01.recipeapplication.comparator.ImageTestComparator;
import com.askie01.recipeapplication.configuration.ImageValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedImageMapperDefaultTestConfiguration;
import com.askie01.recipeapplication.mapper.ImageMapper;
import com.askie01.recipeapplication.model.value.HasImage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ValidatedImageMapperDefaultTestConfiguration.class,
        ImageValueTestComparatorTestConfiguration.class
})
@TestPropertySource(locations = "classpath:validated-image-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedImageMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedImageMapperIntegrationTest {

    private HasImage source;
    private HasImage target;
    private final ImageMapper mapper;
    private final ImageTestComparator comparator;

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
        final boolean equalImage = comparator.compare(source, target);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("map method should not map source image to target image when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceImageToTargetImage() {
        source.setImage(new byte[6 * 1024 * 1024]);
        mapper.map(source, target);
        final boolean equalImage = comparator.compare(source, target);
        assertFalse(equalImage);
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