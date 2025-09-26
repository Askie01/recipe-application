package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasDescriptionTestBuilder;
import com.askie01.recipeapplication.comparator.DescriptionTestComparator;
import com.askie01.recipeapplication.configuration.DescriptionValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.NonBlankDescriptionValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedDescriptionMapperConfiguration;
import com.askie01.recipeapplication.mapper.DescriptionMapper;
import com.askie01.recipeapplication.model.value.HasDescription;
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
        ValidatedDescriptionMapperConfiguration.class,
        NonBlankDescriptionValidatorConfiguration.class,
        DescriptionValueTestComparatorTestConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.description-type=validated-description",
        "component.validator.description-type=non-blank-description"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedDescriptionMapper integration tests")
class ValidatedDescriptionMapperIntegrationTest {

    private final DescriptionMapper mapper;
    private HasDescription source;
    private HasDescription target;

    private final DescriptionTestComparator descriptionComparator;

    @BeforeEach
    void setUp() {
        this.source = HasDescriptionTestBuilder.builder()
                .description("source description")
                .build();
        this.target = HasDescriptionTestBuilder.builder()
                .description("target description")
                .build();
    }

    @Test
    @DisplayName("map method should map source description to target description when source is valid")
    void map_whenSourceIsValid_mapsSourceDescriptionToTargetDescription() {
        mapper.map(source, target);
        final boolean equalDescriptions = descriptionComparator.compare(source, target);
        assertTrue(equalDescriptions);
    }

    @Test
    @DisplayName("map method should not map source description to target description when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceDescriptionToTargetDescription() {
        source.setDescription("");
        mapper.map(source, target);
        final boolean equalDescriptions = descriptionComparator.compare(source, target);
        assertFalse(equalDescriptions);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source description is null")
    void map_whenSourceDescriptionIsNull_throwsNullPointerException() {
        source.setDescription(null);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}